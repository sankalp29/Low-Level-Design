package com.threadsafecounter;

public class SynchronizedCounter implements ICounter<Integer> {
    private volatile int counter;

    @Override
    public synchronized void increment() {
        counter++;
    }

    @Override
    public synchronized void decrement() {
        counter--;
    }

    @Override
    public Integer get() {
        return counter;
    }

    public SynchronizedCounter() {
        this.counter = 0;
    }
}
