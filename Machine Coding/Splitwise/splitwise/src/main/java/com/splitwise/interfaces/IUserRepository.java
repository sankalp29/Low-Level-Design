package com.splitwise.interfaces;

import java.util.Map;

import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.model.User;

public interface IUserRepository {

    User getUserById(String userId) throws UserNotFoundException;

    void save(User user);

    public Map<String, Double> getUserBalance(String userId);

    public void saveExpense(String paidBy, String paidFor, Double amount);
}