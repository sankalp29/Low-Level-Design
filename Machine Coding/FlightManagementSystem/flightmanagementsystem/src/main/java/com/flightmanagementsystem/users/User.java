package com.flightmanagementsystem.users;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class User {
    private String userId;
    private String name;
    private String email;
    private Account account;

    public User(String name, String email, Account account) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.account = account;
    }
}
