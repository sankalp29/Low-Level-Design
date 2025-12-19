package com.irctcsystem.service;

import java.util.Optional;
import java.util.UUID;

import com.irctcsystem.constants.Role;
import com.irctcsystem.model.User;
import com.irctcsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String name, String email, Role role) {
        verifyDetails(name, email);
        User user = new User(UUID.randomUUID().toString(), name, email, role);
        userRepository.save(user);

        return user;
    }

    public Optional<User> getById(String id) {
        return userRepository.getById(id);
    }

    private void verifyDetails(String name, String email) {
        if (name == null || name.isBlank() || email == null || email.isBlank()) 
            throw new IllegalArgumentException("name & email cannot be null / blank");
    }
}
