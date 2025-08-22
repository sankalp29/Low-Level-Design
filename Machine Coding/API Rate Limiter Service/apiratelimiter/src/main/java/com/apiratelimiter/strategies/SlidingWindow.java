package com.apiratelimiter.strategies;

import java.time.LocalDateTime;
import java.util.Deque;

import com.apiratelimiter.RateLimitInfo;
import com.apiratelimiter.RateLimits;
import com.apiratelimiter.user.User;

public class SlidingWindow implements RateLimitingAlgorithmStrategy {

    @Override
    public boolean isRateLimited(Deque<LocalDateTime> requests, User user) {
        RateLimitInfo rateLimitInfo = RateLimits.getRateLimits(user);
        Long window = rateLimitInfo.getNumOfSeconds();
        Integer allowedRequests = rateLimitInfo.getNumOfRequests();
        System.out.println("Request Timestamp : " + LocalDateTime.now());
        LocalDateTime lastAllowedTime = LocalDateTime.now().minusSeconds(window);
        
        while (!requests.isEmpty() && requests.peekFirst().isBefore(lastAllowedTime)) {
            System.out.println("Polled request at : " + requests.pollFirst());
        }

        return requests.size() >= allowedRequests;
    }
    
}
