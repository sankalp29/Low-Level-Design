package com.productsearch.users;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString (exclude={"id", "account"})
public abstract class User {
    private final String id;
    private String name;
    private String email;
    private final Account account;

    public User(String name, String email, Account account) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.account = account;
    }
}
