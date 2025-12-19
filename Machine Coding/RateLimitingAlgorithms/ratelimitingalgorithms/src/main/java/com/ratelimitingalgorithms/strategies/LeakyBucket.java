package com.ratelimitingalgorithms.strategies;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.ratelimitingalgorithms.RateLimitingStrategy;

import lombok.AllArgsConstructor;

public class LeakyBucket implements RateLimitingStrategy {
    private final Integer DEFAULT_REQUEST_LIMIT;
    private final Integer DEFAULT_LEAK_RATE;
    private final Map<String, UserBucket> userBuckets;
    final ScheduledExecutorService scheduler;

    private static class UserBucket {
        Integer count = 0;
        final Integer requestLimit;
        final Integer leakRate;
        final Queue<RequestBody> queue;
        ScheduledFuture<?> leakTask;    
        // final ScheduledExecutorService executor;
        final ReentrantLock lock;

        public UserBucket(final Integer requestLimit, final Integer leakRate) {
            this.requestLimit = requestLimit;
            this.leakRate = leakRate;
            this.queue = new LinkedList<>();
            // this.executor = new ScheduledThreadPoolExecutor(1);
            // executor.scheduleWithFixedDelay(this::task, 0, 3, TimeUnit.SECONDS);
            this.lock = new ReentrantLock();
            this.leakTask = null;
        }

        // private void task() {
        //     lock.lock();
        //     try {
        //         if (!queue.isEmpty()) {
        //             System.out.println("Request" + queue.poll() + " is processed");
        //         }    
        //     } finally {
        //         lock.unlock();
        //     }
        // }
    }

    @AllArgsConstructor
    private static class RequestBody {
        Integer requestId;
    }

    @Override
    public boolean allowRequest(String userId) {
        userBuckets.computeIfAbsent(userId, userBucket -> new UserBucket(DEFAULT_REQUEST_LIMIT, DEFAULT_LEAK_RATE));
        UserBucket userBucket = userBuckets.get(userId);
        userBucket.lock.lock();
        try {
            if (userBucket.queue.size() == userBucket.requestLimit) return false;
            RequestBody requestBody = new RequestBody(++userBucket.count);
            userBucket.queue.add(requestBody);

            if (userBucket.leakTask == null || userBucket.leakTask.isDone()) {
                startLeaking(userId, userBucket);
            }
        } finally {
            userBucket.lock.unlock();
        }
        return true;
    }

    private void startLeaking(String userId, UserBucket userBucket) {
        userBucket.leakTask = scheduler.scheduleAtFixedRate(() -> {
            userBucket.lock.lock();
            try {
                if (userBucket.queue.isEmpty()) {
                    if (userBucket.leakTask != null && !userBucket.leakTask.isDone()) {
                        userBucket.leakTask.cancel(false);
                        userBucket.leakTask = null;
                    }
                    return;
                }

                RequestBody requestBody = userBucket.queue.poll();
                processRequest(userId, requestBody);
            } finally {
                userBucket.lock.unlock();
            }
        }, 0, userBucket.leakRate, TimeUnit.SECONDS);
    }

    private void processRequest(String userId, RequestBody requestBody) {
        System.out.println("Request for " + userId + " : " + requestBody + " : Processed");
    }

    public LeakyBucket(final Integer REQUEST_LIMIT, final Integer LEAK_RATE) {
        this.DEFAULT_REQUEST_LIMIT = REQUEST_LIMIT;
        this.DEFAULT_LEAK_RATE = LEAK_RATE;
        this.userBuckets = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new ThreadFactory() {
                private int count = 0;
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Leaky-Bucket-Thread-" + ++count);
                    thread.setDaemon(true);
                    return thread;
                }
            }
        );
    }
}