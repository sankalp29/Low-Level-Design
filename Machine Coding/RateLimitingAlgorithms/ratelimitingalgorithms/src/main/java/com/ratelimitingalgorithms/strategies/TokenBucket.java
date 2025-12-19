package com.ratelimitingalgorithms.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.ratelimitingalgorithms.RateLimitingStrategy;

public class TokenBucket implements RateLimitingStrategy {
    private final long DEFAULT_MAX_CAPACITY;
    private final long DEFAULT_REFRESH_TOKENS;
    private final long DEFAULT_REFRESH_RATE;
    private final Map<String, UserBucket> userBuckets;
    
    private static class UserBucket {
        final ReentrantLock lock;
        long tokens;
        final long capacity;
        final long refreshTokens;
        final long refreshRate;
        LocalDateTime lastRefreshTime;

        public UserBucket(final long capacity, final long refreshTokens, final long refreshRate) {
            this.tokens = capacity;
            this.capacity = capacity;
            this.refreshTokens = refreshTokens;
            this.refreshRate = refreshRate;
            this.lastRefreshTime = LocalDateTime.now();
            this.lock = new ReentrantLock();
        }
    }

    @Override
    public boolean allowRequest(String userId) {
        userBuckets.computeIfAbsent(userId, user -> new UserBucket(DEFAULT_MAX_CAPACITY, DEFAULT_REFRESH_TOKENS, DEFAULT_REFRESH_RATE));
        
        UserBucket userBucket = userBuckets.get(userId);
        userBucket.lock.lock();
        try {
            refill(userBucket);
            if (userBucket.tokens == 0) return false;
            userBucket.tokens = userBucket.tokens - 1;
        } finally {
            userBucket.lock.unlock();
        }

        return true;
    }

    private void refill(UserBucket userBucket) {    
        long refillsToBeDone = Duration.between(userBucket.lastRefreshTime, LocalDateTime.now()).toSeconds() / userBucket.refreshRate;
        userBucket.tokens = Math.min(userBucket.capacity, userBucket.tokens + refillsToBeDone * userBucket.refreshTokens);
        if (refillsToBeDone > 0) userBucket.lastRefreshTime = LocalDateTime.now();
    }

    public TokenBucket(final long MAX_CAPACITY, final long REFRESH_TOKENS, final long REFRESH_RATE) {
        this.DEFAULT_MAX_CAPACITY = MAX_CAPACITY;
        this.DEFAULT_REFRESH_TOKENS = REFRESH_TOKENS;
        this.DEFAULT_REFRESH_RATE = REFRESH_RATE;
        this.userBuckets = new ConcurrentHashMap<>();
    }
}