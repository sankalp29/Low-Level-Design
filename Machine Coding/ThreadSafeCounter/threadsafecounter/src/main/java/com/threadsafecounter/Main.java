package com.threadsafecounter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ICounter counter = new SynchronizedCounter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.increment();
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) counter.decrement();
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(counter.get());
        
        counter.increment();
        
        System.out.println(counter.get());
    }
}