package com.ratelimiter;

import com.ratelimiter.strategies.SlidingWindowAlgorithmStrategy;

public class Main {
    public static void main(String[] args) {
        // testHappyCase();
        // testSingleThreadedSingleUser();
        // testMultithreadedSingleUser();
        testMultithreadedMultiUser();
    }

    private static void testHappyCase() {
        RateLimiterService service = new RateLimiterService(SlidingWindowAlgorithmStrategy.getInstance());
        for (int i = 0; i < 15; i++) {
            System.out.println("Request for A : " + i + " : " + service.allowRequest("A"));
            System.out.println("Request for B : " + i + " : " + service.allowRequest("B"));
        }
    }

    private static void testSingleThreadedSingleUser() {
        RateLimiterService service = new RateLimiterService(SlidingWindowAlgorithmStrategy.getInstance());
        Runnable runnable = () -> {
            for (int i = 0; i < 15; i++) {
                System.out.println("Request: " + i + " : " + service.allowRequest("A"));
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            for (int i = 15; i < 30; i++) {
                System.out.println("Request: " + i + " : " + service.allowRequest("A"));
            }
        };

        Thread t1 = new Thread(runnable);
        t1.start();
    }

    private static void testMultithreadedSingleUser() {
        RateLimiterService service = new RateLimiterService(SlidingWindowAlgorithmStrategy.getInstance());
        Runnable runnable1 = () -> {
            for (int i = 0; i < 15; i++) {
                System.out.println("Request T1 : " + i + " : " + service.allowRequest("A"));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
            for (int i = 15; i < 30; i++) {
                System.out.println("Request T1 : " + i + " : " + service.allowRequest("A"));
            }
        };

        Runnable runnable2 = () -> {
            for (int i = 0; i < 15; i++) {
                System.out.println("Request T2 : " + i + " : " + service.allowRequest("A"));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
            for (int i = 15; i < 30; i++) {
                System.out.println("Request T2 : " + i + " : " + service.allowRequest("A"));
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();
    }

    private static void testMultithreadedMultiUser() {
        RateLimiterService service = new RateLimiterService(SlidingWindowAlgorithmStrategy.getInstance());
        Runnable runnable1 = () -> {
            for (int i = 0; i < 15; i++) {
                System.out.println("A : Request T1 : " + i + " : " + service.allowRequest("A"));
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            for (int i = 15; i < 30; i++) {
                System.out.println("B : Request T1 : " + i + " : " + service.allowRequest("B"));
            }
        };

        Runnable runnable2 = () -> {
            for (int i = 0; i < 15; i++) {
                System.out.println("B : Request T2 : " + i + " : " + service.allowRequest("B"));
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            for (int i = 15; i < 30; i++) {
                System.out.println("A : Request T2 : " + i + " : " + service.allowRequest("A"));
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();
    }
}