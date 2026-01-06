package com.threadsafecounter;

public interface ICounter<T> {
    void increment();
    void decrement();
    T get();
}
