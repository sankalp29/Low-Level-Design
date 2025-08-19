package com.apiratelimiter.strategies;

import java.time.LocalDateTime;
import java.util.Deque;

import com.apiratelimiter.User;

public class TokenBucket implements RateLimitingAlgorithmStrategy {
    @Override
    public boolean isRateLimited(Deque<LocalDateTime> requests, User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
