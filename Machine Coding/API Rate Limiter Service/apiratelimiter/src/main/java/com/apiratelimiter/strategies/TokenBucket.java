package com.apiratelimiter.strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Deque;

import com.apiratelimiter.RateLimitInfo;
import com.apiratelimiter.RateLimits;
import com.apiratelimiter.user.User;

public class TokenBucket implements RateLimitingAlgorithmStrategy {

    private static final Integer REFRESH_TOKEN = 5;

    private Integer tokensPresent;
    private LocalDateTime lastRefreshTime;

    public TokenBucket() {
        lastRefreshTime = LocalDateTime.now();
        tokensPresent = REFRESH_TOKEN;
    }

    @Override
    public boolean isRateLimited(Deque<LocalDateTime> deque, User user) {
        RateLimitInfo rateLimitInfo = RateLimits.getRateLimits(user);
        int bucketCapacity = rateLimitInfo.getNumOfRequests();
        Long refreshTime = rateLimitInfo.getNumOfSeconds();

        int refreshes = (int) Duration.between(lastRefreshTime, LocalDateTime.now()).toMinutes();
        if (refreshes > 0) lastRefreshTime = LocalDateTime.now();
        int tokensToAdd = refreshes * REFRESH_TOKEN;
        tokensPresent = Math.min(bucketCapacity, tokensPresent + tokensToAdd);

        if (tokensPresent > 0) {
            tokensPresent--;
            return false;
        }
        return true;
    }
}