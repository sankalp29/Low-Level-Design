package com.splitwise.repository;

import java.util.HashMap;
import java.util.Map;

import com.splitwise.constants.ExceptionConstants;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.interfaces.IUserRepository;
import com.splitwise.model.User;

public class UserRepository implements IUserRepository {
    private final Map<String, User> users;
    
    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        if (!users.containsKey(userId)) throw new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND_EXCEPTION);
        return users.get(userId);
    }

    @Override
    public void save(User user) {
        String userId = user.getId();
        users.put(userId, user);
    }

    public UserRepository() {
        users = new HashMap<>();
    }
}
