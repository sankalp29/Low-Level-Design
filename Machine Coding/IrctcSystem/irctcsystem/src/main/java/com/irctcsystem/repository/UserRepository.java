package com.irctcsystem.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.irctcsystem.model.User;

public class UserRepository {
    private final Map<String, User> users;

    public void save(User user) {
        users.put(user.getId(), user);
    }

    public Optional<User> getById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public UserRepository() {
        users = new HashMap<>();
    }    
}
