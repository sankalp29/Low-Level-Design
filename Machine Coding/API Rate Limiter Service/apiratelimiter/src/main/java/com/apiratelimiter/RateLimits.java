package com.apiratelimiter;

public class RateLimits {
    public static RateLimitInfo getRateLimits(User user) {
        UserType userType = user.getUserType();
        if (userType.equals(UserType.NORMAL)) {
            return new RateLimitInfo(10, 60L);
        }
        if (userType.equals(UserType.PREMIUM)) {
            return new RateLimitInfo(50, 60L);
        }

        return new RateLimitInfo(100, 60L);
    }
}