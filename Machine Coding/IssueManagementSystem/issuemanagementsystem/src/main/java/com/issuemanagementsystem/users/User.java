package com.issuemanagementsystem.users;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude={"id", "registeredOn"})
public abstract class User {
    private final String id;
    private String name;
    private String email;
    private final LocalDateTime registeredOn;

    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.registeredOn = LocalDateTime.now();
    }
}
