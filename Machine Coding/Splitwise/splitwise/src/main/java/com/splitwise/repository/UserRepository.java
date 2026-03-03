package com.splitwise.repository;

import java.util.HashMap;
import java.util.Map;

import com.splitwise.constants.ExceptionConstants;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.interfaces.IUserRepository;
import com.splitwise.model.User;

public class UserRepository implements IUserRepository {
    private final Map<String, User> users;
    private final Map<String, Map<String, Double>> userBalance;
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

    @Override
    public Map<String, Double> getUserBalance(String userId) {
        return userBalance.getOrDefault(userId, new HashMap<>());
    }

    @Override
    public void saveExpense(String paidBy, String paidFor, Double amount) {
        userBalance.putIfAbsent(paidBy, new HashMap<>());
        Double current = userBalance.get(paidBy).getOrDefault(paidFor, 0.0);
        userBalance.get(paidBy).put(paidFor, current + amount);

        userBalance.putIfAbsent(paidFor, new HashMap<>());
        current = userBalance.get(paidFor).getOrDefault(paidBy, 0.0);
        userBalance.get(paidFor).put(paidBy, current - amount);
    }

    public UserRepository() {
        users = new HashMap<>();
        userBalance = new HashMap<>();
    }
}
