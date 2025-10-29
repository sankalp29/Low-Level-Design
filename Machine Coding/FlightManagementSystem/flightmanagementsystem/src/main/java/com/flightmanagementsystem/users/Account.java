package com.flightmanagementsystem.users;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private final String id;
    private String username;
    private LocalDateTime createdAt;

    public Account(String username) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.createdAt = LocalDateTime.now();
    }
}
