package com.ratelimitingalgorithms.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.ratelimitingalgorithms.RateLimitingStrategy;

public class FixedWindowCounter implements RateLimitingStrategy {
    private final long DEFAULT_WINDOW_SIZE;
    private final long DEFAULT_REQUEST_LIMIT;
    private Map<String, UserCounter> userCounters;

    private static class UserCounter {
        final long windowSize;
        final long requestLimit;
        LocalDateTime windowStart;
        long requestsAllowed;
        final ReentrantLock lock;

        public UserCounter(final long windowSize, final long requestLimit) {
            this.windowSize = windowSize;
            this.requestLimit = requestLimit;
            this.windowStart = LocalDateTime.now();
            this.requestsAllowed = 0;
            this.lock = new ReentrantLock();
        }
    }

    @Override
    public boolean allowRequest(String userId) {
        userCounters.computeIfAbsent(userId, user -> new UserCounter(DEFAULT_WINDOW_SIZE, DEFAULT_REQUEST_LIMIT));
        UserCounter userCounter = userCounters.get(userId);
        userCounter.lock.lock();
        try {
            resetWindow(userCounter);
            if (userCounter.requestsAllowed == userCounter.requestLimit) return false;
            userCounter.requestsAllowed++;
        } finally {
            userCounter.lock.unlock();
        }
        return true;
    }

    private void resetWindow(UserCounter userCounter) {
        long duration = Duration.between(userCounter.windowStart, LocalDateTime.now()).toSeconds();
        if (duration >= userCounter.windowSize) {
            userCounter.windowStart = LocalDateTime.now();
            userCounter.requestsAllowed = 0;
        }
    }

    public FixedWindowCounter(final long WINDOW_SIZE, final long REQUEST_LIMIT) {
        this.DEFAULT_WINDOW_SIZE = WINDOW_SIZE;
        this.DEFAULT_REQUEST_LIMIT = REQUEST_LIMIT;
        this.userCounters = new ConcurrentHashMap<>();
    }
}
