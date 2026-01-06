package com.threadsafecounter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements ICounter<Integer> {

    private final AtomicInteger counter;

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public void decrement() {
        counter.decrementAndGet();
    }

    @Override
    public Integer get() {
        return counter.get();
    }

    public AtomicCounter() {
        this.counter = new AtomicInteger(0);
    }
}
