package com.splitwise.interfaces;

import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.model.User;

public interface IUserRepository {

    User getUserById(String userId) throws UserNotFoundException;

    void save(User user);

}