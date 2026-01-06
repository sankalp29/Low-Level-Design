package com.producerconsumerqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<T> implements IBlockingQueue<T> {

    private final Queue<T> queue;
    private final int capacity;
    private final ReentrantLock lock;
    private final Condition notFullSignal;
    private final Condition notEmptySignal;

    @Override
    public void put(T value) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                System.out.println("**** WAITING to PUT ****\n");
                notFullSignal.await();
            }
            System.out.println("ADDED : " + value);
            queue.add(value);
            Thread.sleep(1000);
            notEmptySignal.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("==== WAITING to TAKE ====\n");
                notEmptySignal.await();
            }
            
            T value = queue.poll();
            System.out.println("POLLED : " + value);
            Thread.sleep(1000);
            notFullSignal.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }

    public CustomBlockingQueue(int capacity) {
        queue = new LinkedList<>();
        this.capacity = capacity;
        this.lock = new ReentrantLock();
        this.notFullSignal = lock.newCondition();
        this.notEmptySignal = lock.newCondition();
    }
}
