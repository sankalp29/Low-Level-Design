package com.inmemorycache;

public interface CacheStrategy<T, U> {
    U get(T key);
    void put(T key, U value, long ttl);
}