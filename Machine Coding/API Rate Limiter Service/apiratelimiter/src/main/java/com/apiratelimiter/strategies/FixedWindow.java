package com.apiratelimiter.strategies;

import java.time.LocalDateTime;
import java.util.Deque;

import com.apiratelimiter.RateLimitInfo;
import com.apiratelimiter.RateLimits;
import com.apiratelimiter.user.User;

public class FixedWindow implements RateLimitingAlgorithmStrategy {
    @Override
    public boolean isRateLimited(Deque<LocalDateTime> requests, User user) {
        RateLimitInfo rateLimitInfo = RateLimits.getRateLimits(user);
        Long window = rateLimitInfo.getNumOfSeconds();
        Integer allowedRequests = rateLimitInfo.getNumOfRequests();
        Integer currentMinute = LocalDateTime.now().getMinute();
        
        while (!requests.isEmpty() && requests.peekFirst().getMinute() < currentMinute) {
            requests.pollFirst();
        }

        return requests.size() >= allowedRequests;
    }
}