package com.splitwise.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group {
    private static Integer groupNumber = 0;
    private final String id;
    private String name;
    private final LocalDateTime createdAt;
    
    public Group(String name) {
        this.id = "group-" + (++groupNumber);
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }
}