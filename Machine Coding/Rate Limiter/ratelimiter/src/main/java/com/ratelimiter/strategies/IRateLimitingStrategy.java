package com.ratelimiter.strategies;

public interface IRateLimitingStrategy {
    public boolean allowRequest(String userId);
}
