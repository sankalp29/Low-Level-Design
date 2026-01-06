package com.keyvaluestore;

public interface IKeyValueStore<K,V> {
    void put(K key, V value);
    void put(K key, V value, long ttlMillis);
    V get(K key);
}
