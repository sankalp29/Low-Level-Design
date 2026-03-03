package com.splitwise.model;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User implements Observable {
    private static final AtomicInteger userIdCount = new AtomicInteger(0);
    private final String id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = "User-" + (userIdCount.incrementAndGet());
        this.name = name;
        this.email = email;
    }

    @Override
    public
    void notify(String message) {
        System.out.println("[Notification : " + id + "] " + message);
    }
}
