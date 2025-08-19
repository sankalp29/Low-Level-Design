package com.apiratelimiter;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import com.apiratelimiter.strategies.RateLimitingAlgorithmStrategy;

import lombok.Setter;

public class RateLimiter {
    private static ConcurrentHashMap<User, ConcurrentHashMap<String, Deque<LocalDateTime>>> userRequestMap;
    private static RateLimiter INSTANCE;
    
    @Setter
    private RateLimitingAlgorithmStrategy rateLimitingAlgorithmStrategy;

    public static RateLimiter getInstance(RateLimitingAlgorithmStrategy rateLimitingAlgorithmStrategy) {
        if (INSTANCE == null) {
            synchronized (RateLimiter.class) {
                if (INSTANCE == null) INSTANCE = new RateLimiter(rateLimitingAlgorithmStrategy);
            }
        }
        return INSTANCE;
    }

    private RateLimiter(RateLimitingAlgorithmStrategy rateLimitingAlgorithmStrategy) {
        userRequestMap = new ConcurrentHashMap<>();
        this.rateLimitingAlgorithmStrategy = rateLimitingAlgorithmStrategy;
    }

    public boolean canCall(User user, String service) {
        userRequestMap.putIfAbsent(user, new ConcurrentHashMap<String, Deque<LocalDateTime>>());
        userRequestMap.get(user).putIfAbsent(service, new ArrayDeque<LocalDateTime>());

        synchronized (userRequestMap.get(user).get(service)) {
            if (!rateLimitingAlgorithmStrategy.isRateLimited(userRequestMap.get(user).get(service), user)) {
                userRequestMap.get(user).get(service).offerLast(LocalDateTime.now());
                return true;
            }
            return false;    
        }
    }
}
