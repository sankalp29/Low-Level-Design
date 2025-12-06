package com.splitwise.interfaces;

import java.util.List;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.model.User;

public interface IUserService {

    String createUser(String name, String email);

    void updateUser(String userId, String name, String email) throws UserNotFoundException;

    User getUserById(String userId) throws UserNotFoundException;

    void addExpense(String paidBy, String paidTo, Double amount, SplitType splitType, List<Double> userSplit)
            throws UserNotFoundException;

}