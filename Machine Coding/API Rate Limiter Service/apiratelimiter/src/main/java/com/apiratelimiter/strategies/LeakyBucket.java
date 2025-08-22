package com.apiratelimiter.strategies;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.apiratelimiter.RateLimitInfo;
import com.apiratelimiter.RateLimits;
import com.apiratelimiter.user.User;

public class LeakyBucket implements RateLimitingAlgorithmStrategy {
    private final Queue<LocalDateTime> queue;
    private final ScheduledExecutorService scheduledExecutorService;
    
    public LeakyBucket() {
        this.queue = new LinkedList<>();
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
        scheduledExecutorService.scheduleAtFixedRate(() -> processRequests(), 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public boolean isRateLimited(Deque<LocalDateTime> deque, User user) {
        RateLimitInfo rateLimitInfo = RateLimits.getRateLimits(user);
        int bucketCapacity = rateLimitInfo.getNumOfRequests();

        if (queue.size() < bucketCapacity) {
            queue.add(deque.poll());
            return false;
        }
        return true;
    }

    private void processRequests() {
        while (!queue.isEmpty()) {
            System.out.println("Processing request: " + queue.poll());
        }
    }
}