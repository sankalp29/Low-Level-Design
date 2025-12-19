package com.ratelimitingalgorithms;

public interface RateLimitingStrategy {
    boolean allowRequest(String userId);
}
