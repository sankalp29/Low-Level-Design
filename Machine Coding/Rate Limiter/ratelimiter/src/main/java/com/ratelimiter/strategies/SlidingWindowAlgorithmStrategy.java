package com.ratelimiter.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowAlgorithmStrategy implements IRateLimitingStrategy {
    private final Integer maxRequests;
    private final Integer windowInSec;
    private final ConcurrentHashMap<String, UserWindow> userRequestLog;

    private static class UserWindow {
        final ConcurrentLinkedQueue<LocalDateTime> log = new ConcurrentLinkedQueue<>();
        final ReentrantLock lock = new ReentrantLock();
    }

    @Override
    public boolean allowRequest(String userId) {
        userRequestLog.putIfAbsent(userId, new UserWindow());
        UserWindow userWindow = userRequestLog.get(userId);

        userWindow.lock.lock();
        try {
            while (!userWindow.log.isEmpty() && (Duration.between(userWindow.log.peek(), LocalDateTime.now()).toSeconds() >= windowInSec)) {
                userWindow.log.poll();
            }
            if (userWindow.log.size() < maxRequests) {
                userWindow.log.add(LocalDateTime.now());
                userRequestLog.put(userId, userWindow);
                return true;
            }
            return false;
        } finally {
            userWindow.lock.unlock();
        }
        
    }

    private boolean isOutOfWindow(LocalDateTime logTime) {
        return Duration.between(logTime, LocalDateTime.now()).toSeconds() >= windowInSec;
    }

    public SlidingWindowAlgorithmStrategy(final Integer requests, final Integer windowInSec) {
        this.userRequestLog = new ConcurrentHashMap<>();
        this.maxRequests = requests;
        this.windowInSec = windowInSec;
    }
}