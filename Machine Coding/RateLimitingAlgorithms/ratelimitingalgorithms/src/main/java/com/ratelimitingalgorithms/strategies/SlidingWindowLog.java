package com.ratelimitingalgorithms.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.ratelimitingalgorithms.RateLimitingStrategy;

public class SlidingWindowLog implements RateLimitingStrategy {
    private final long DEFAULT_REQUESTS_LIMIT;
    private final long DEFAULT_WINDOW_SIZE;
    private final Map<String, UserLog> userLogs;

    private static class UserLog {
        final long requestLimit;
        final long windowSize;
        final Queue<LocalDateTime> requestQueue;
        final ReentrantLock lock;

        public UserLog(final long requestLimit, final long windowSize) {
            this.requestLimit = requestLimit;
            this.windowSize = windowSize;
            this.requestQueue = new LinkedList<>();
            this.lock = new ReentrantLock();
        }
    }

    @Override
    public boolean allowRequest(String userId) {
        userLogs.computeIfAbsent(userId, user -> new UserLog(DEFAULT_REQUESTS_LIMIT, DEFAULT_WINDOW_SIZE));
        UserLog userLog = userLogs.get(userId);
        userLog.lock.lock();
        try {
            cleanupEntries(userLog);
            if (userLog.requestQueue.size() >= userLog.requestLimit) return false;
            userLog.requestQueue.add(LocalDateTime.now());    
        } finally {
            userLog.lock.unlock();
        }
        
        return true;
    }

    private void cleanupEntries(UserLog userLog) {
        Queue<LocalDateTime> requestQueue = userLog.requestQueue;
        while (!requestQueue.isEmpty() && Duration.between(requestQueue.peek(), LocalDateTime.now()).toSeconds() >= userLog.windowSize) {
            requestQueue.poll();
        }
    }

    public SlidingWindowLog(final long REQUESTS_LIMIT, final long WINDOW_SIZE) {
        this.DEFAULT_REQUESTS_LIMIT = REQUESTS_LIMIT;
        this.DEFAULT_WINDOW_SIZE = WINDOW_SIZE;
        this.userLogs = new ConcurrentHashMap<>();
    }
}