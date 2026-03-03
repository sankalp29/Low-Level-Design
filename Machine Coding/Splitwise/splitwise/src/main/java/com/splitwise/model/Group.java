package com.splitwise.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group {
    private static final AtomicInteger groupNumber = new AtomicInteger(0);
    private final String id;
    private String name;
    private final LocalDateTime createdAt;
    
    public Group(String name) {
        this.id = "Group-" + (groupNumber.incrementAndGet());
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }
}