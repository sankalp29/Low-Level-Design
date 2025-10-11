package com.ratelimiter.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketAlgorithmStrategy implements IRateLimitingStrategy {
    
    private final ConcurrentHashMap<String, TokenBucketInfo> userTokenBucketMap;
    private final Integer capacity;
    private final Integer refillPerSec;

    private static class TokenBucketInfo {
        private Integer capacity;
        private Integer tokens;
        private Integer refillPerSec;
        private LocalDateTime lastRefreshTime;
        final ReentrantLock lock;

        public TokenBucketInfo(Integer capacity, Integer refillPerSec) {
            this.capacity = capacity;
            this.refillPerSec = refillPerSec;
            this.tokens = capacity;
            this.lastRefreshTime = LocalDateTime.now();
            lock = new ReentrantLock();
        }

        private void resetLastRefreshTime() {
            this.lastRefreshTime = LocalDateTime.now();
        }

        public void refillTokens() {
            int elapsedTime = (int) Duration.between(lastRefreshTime, LocalDateTime.now()).toSeconds();
            int refillTokens = elapsedTime * refillPerSec;

            tokens = Math.min(capacity, tokens + refillTokens);
            resetLastRefreshTime();
        }

        public boolean useToken() {
            lock.lock();
            try {
                refillTokens();
                if (tokens == 0) return false;
                tokens--;
                return true;
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public boolean allowRequest(String userId) {
        userTokenBucketMap.putIfAbsent(userId, new TokenBucketInfo(capacity, refillPerSec));
        TokenBucketInfo tokenBucketInfo = userTokenBucketMap.get(userId);

        return tokenBucketInfo.useToken();
    }

    public TokenBucketAlgorithmStrategy(Integer capacity, Integer refillPerSec) {
        userTokenBucketMap = new ConcurrentHashMap<>();
        this.capacity = capacity;
        this.refillPerSec = refillPerSec;
    }
}
