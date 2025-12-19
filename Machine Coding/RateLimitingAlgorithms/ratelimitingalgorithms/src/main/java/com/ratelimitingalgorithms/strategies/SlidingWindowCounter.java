package com.ratelimitingalgorithms.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.ratelimitingalgorithms.RateLimitingStrategy;

public class SlidingWindowCounter implements RateLimitingStrategy {
    private final long DEFAULT_WINDOW_SIZE;
    private final long DEFAULT_REQUESTS_LIMIT;
    private final Map<String, UserCounter> userCounters;

    private static class UserCounter {
        final long windowSize;
        final long requestLimit;
        long lastWindowRequestCount;
        long requestsAllowed;
        LocalDateTime windowStart;
        final ReentrantLock lock;

        public UserCounter(final long windowSize, final long requestLimit) {
            this.windowSize = windowSize;
            this.requestLimit = requestLimit;
            this.lastWindowRequestCount = 0;
            this.requestsAllowed = 0;
            this.windowStart = LocalDateTime.now();
            this.lock = new ReentrantLock();
        }
    }

    @Override
    public boolean allowRequest(String userId) {
        userCounters.computeIfAbsent(userId, userCounter -> new UserCounter(DEFAULT_WINDOW_SIZE, DEFAULT_REQUESTS_LIMIT));

        UserCounter userCounter = userCounters.get(userId);
        userCounter.lock.lock();
        try {
            resetWindow(userCounter);
            long currentWindowDuration = Duration.between(userCounter.windowStart, LocalDateTime.now()).toSeconds();
            double currentWindowPercentage = (currentWindowDuration * 1.0 / userCounter.windowSize);
            double lastWindowRequests = userCounter.lastWindowRequestCount * (1 - currentWindowPercentage);
            double estimatedRequests = lastWindowRequests + userCounter.requestsAllowed;
            if (estimatedRequests >= userCounter.requestLimit) return false;
            userCounter.requestsAllowed++;
        } finally {
            userCounter.lock.unlock();
        }

        return true;
    }

    private void resetWindow(UserCounter userCounter) {
        long currentWindowDuration = Duration.between(userCounter.windowStart, LocalDateTime.now()).toSeconds();
    
        if (currentWindowDuration >= userCounter.windowSize * 2) {
            // More than 2 windows passed - previous window is irrelevant
            userCounter.lastWindowRequestCount = 0;
            userCounter.requestsAllowed = 0;
            userCounter.windowStart = LocalDateTime.now();
        } else if (currentWindowDuration >= userCounter.windowSize) {
            // Exactly 1 window passed
            userCounter.lastWindowRequestCount = userCounter.requestsAllowed;
            userCounter.requestsAllowed = 0;
            userCounter.windowStart = userCounter.windowStart.plusSeconds(userCounter.windowSize);
        }
    }

    public SlidingWindowCounter(final long WINDOW_SIZE, final long REQUESTS_LIMIT) {
        this.DEFAULT_WINDOW_SIZE = WINDOW_SIZE;
        this.DEFAULT_REQUESTS_LIMIT = REQUESTS_LIMIT;
        this.userCounters = new ConcurrentHashMap<>();
    }
}