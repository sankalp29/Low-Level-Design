package com.ratelimitingalgorithms.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ratelimitingalgorithms.RateLimitingStrategy;

/**
 * Simple leaky bucket without background request execution
 */
public class SimpleLeakyBucket implements RateLimitingStrategy {
    private final Map<String, UserBucket> userBuckets;
    private final Integer LEAK_RATE_PER_SEC;
    private final Integer QUEUE_HOLD_CAPACITY;

    private static class UserBucket {
        Integer requestCount;
        LocalDateTime lastLeakedAt;

        public UserBucket() {
            this.requestCount = 0;
            this.lastLeakedAt = LocalDateTime.now();
        }
    }

    @Override
    public boolean allowRequest(String userId) {
        UserBucket userBucket = userBuckets.computeIfAbsent(userId, bucket -> new UserBucket());

        synchronized (userBucket) {
            int leaked = (int) Duration.between(userBucket.lastLeakedAt, LocalDateTime.now()).toSeconds() * LEAK_RATE_PER_SEC;
            userBucket.requestCount = Math.max(userBucket.requestCount - leaked, 0);
            userBucket.lastLeakedAt = LocalDateTime.now();

            if (userBucket.requestCount + 1 > QUEUE_HOLD_CAPACITY) return false;
            userBucket.requestCount++;
            return true;
        }
    }

    public SimpleLeakyBucket(final Integer LEAK_RATE_PER_SEC, final Integer QUEUE_HOLD_CAPACITY) {
        this.LEAK_RATE_PER_SEC = LEAK_RATE_PER_SEC;
        this.QUEUE_HOLD_CAPACITY = QUEUE_HOLD_CAPACITY;
        this.userBuckets = new ConcurrentHashMap<>();
    }
}
