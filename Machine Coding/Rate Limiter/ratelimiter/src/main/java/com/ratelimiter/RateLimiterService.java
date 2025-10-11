package com.ratelimiter;

import com.ratelimiter.strategies.IRateLimitingStrategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RateLimiterService {
    private final IRateLimitingStrategy rateLimitingStrategy;
    
    public boolean allowRequest(String userId) {
        return rateLimitingStrategy.allowRequest(userId);
    }
}
