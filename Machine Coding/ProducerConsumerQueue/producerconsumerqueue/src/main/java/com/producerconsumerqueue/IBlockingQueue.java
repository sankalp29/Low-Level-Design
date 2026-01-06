package com.producerconsumerqueue;

public interface IBlockingQueue<T> {
    void put(T item) throws InterruptedException;
    T take() throws InterruptedException;
}
