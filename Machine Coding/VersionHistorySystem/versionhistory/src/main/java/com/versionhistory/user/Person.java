package com.versionhistory.user;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude={"name", "email"})
public abstract class Person {
    private final String userId;
    private String name;
    private String email;

    public Person(String name, String email) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }
}
