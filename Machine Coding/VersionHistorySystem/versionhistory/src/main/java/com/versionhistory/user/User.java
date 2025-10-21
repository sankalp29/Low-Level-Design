package com.versionhistory.user;

import lombok.Getter;

@Getter
public class User extends Person {
    private final Account account;

    public User(String name, String email) {
        super(name, email);
        account = new Account();
    }
    
}
