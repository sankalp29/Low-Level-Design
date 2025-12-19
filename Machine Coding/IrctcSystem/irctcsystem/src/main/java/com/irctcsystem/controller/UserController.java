package com.irctcsystem.controller;

import com.irctcsystem.constants.Role;
import com.irctcsystem.model.User;
import com.irctcsystem.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserController {
    private final UserService userService;

    public User createUser(String name, String email, Role role) {
        return userService.createUser(name, email, role);
    }
}
