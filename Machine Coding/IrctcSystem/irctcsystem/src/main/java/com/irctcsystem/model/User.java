package com.irctcsystem.model;

import com.irctcsystem.constants.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private final String id;
    private String name;
    private String email;
    private final Role role;

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }
}
