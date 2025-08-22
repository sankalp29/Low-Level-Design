package com.apiratelimiter.strategies;

import java.time.LocalDateTime;
import java.util.Deque;

import com.apiratelimiter.user.User;

public interface RateLimitingAlgorithmStrategy {
    boolean isRateLimited(Deque<LocalDateTime> requests, User user);
}
