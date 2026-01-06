package com.keyvaluestore;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore<K,V> implements IKeyValueStore<K, V> {
    private final Map<K, Entry<V>> keyToValue;

    @Override
    public V get(K key) {
        Entry<V> entry = keyToValue.get(key);
        if (entry == null) return null;
        if (entry.isExpired()) {
            keyToValue.remove(key);
            return null;
        }

        return entry.getValue();
    }

    @Override
    public void put(K key, V value) {
        Entry<V> entry = keyToValue.get(key);
        if (entry != null) {
            entry.setValue(value);
            entry.setExpiryTime(LocalDateTime.MAX);
        } else {
            entry = new Entry<>(value);
            keyToValue.put(key, entry);
        }
    }

    @Override
    public void put(K key, V value, long ttlMillis) {
        Entry<V> entry = keyToValue.get(key);
        if (entry != null) {
            entry.setValue(value);
            entry.setExpiryTime(LocalDateTime.now().plusSeconds(ttlMillis / 1000));
        } else {
            entry = new Entry<>(value, LocalDateTime.now().plusSeconds(ttlMillis / 1000));
            keyToValue.put(key, entry);
        }
    }

    public KeyValueStore() {
        this.keyToValue = new ConcurrentHashMap<>();
    }
}
