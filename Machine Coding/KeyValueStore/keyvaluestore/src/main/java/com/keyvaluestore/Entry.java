package com.keyvaluestore;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entry<V> {
    private V value; 
    private LocalDateTime expiryTime;

    public boolean isExpired() {
        return !expiryTime.isAfter(LocalDateTime.now());
    }

    public Entry(V value, LocalDateTime expiryTime) {
        this.value = value;
        this.expiryTime = expiryTime;
    }

    public Entry(V value) {
        this.value = value;
        this.expiryTime = LocalDateTime.MAX;
    }
}
