package com.ratelimitingalgorithms;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RateLimiter {
    private final RateLimitingStrategy rateLimitingStrategy;

    public boolean allowRequest(String userId) {
        return rateLimitingStrategy.allowRequest(userId);
    }
}
