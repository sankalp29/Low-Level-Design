package com.splitwise.controller;

import java.util.List;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.interfaces.IUserService;
import com.splitwise.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    public String createUser(String name, String email) {
        return userService.createUser(name, email);
    }
    
    public void updateUser(String userId, String name, String email) throws UserNotFoundException {
        userService.updateUser(userId, name, email);
    }

    public User getUserById(String userId) throws UserNotFoundException {
        return userService.getUserById(userId);
    }

    public void addExpense(String paidBy, String paidTo, Double amount, SplitType splitType, List<Double> userSplit) throws UserNotFoundException {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0.");
        userService.addExpense(paidBy, paidTo, amount, splitType, userSplit);
    }
}
