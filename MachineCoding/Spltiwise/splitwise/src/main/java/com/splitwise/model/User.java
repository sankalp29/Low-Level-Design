package com.splitwise.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User extends Observer {
    private static Integer userIdCount = 0;
    private final String id;
    private String name;
    private String email;
    private final Map<String, Double> userBalances;

    public boolean addExpense(String description, Double amount, String userId, SplitType splitType, List<Double> userSplit) throws UserNotFoundException {        
        String notificationMessage = "[Expense added] : " + description + " Rs. " + amount;
        notify(notificationMessage);
        userBalances.putIfAbsent(userId, 0.0);
        userBalances.put(userId, userBalances.get(userId) + amount);
        return true;
    }

    public User(String name, String email) {
        this.id = "User-" + (++userIdCount);
        this.name = name;
        this.email = email;
        this.userBalances = new HashMap<>();
    }

    @Override
    public
    void notify(String message) {
        System.out.println("[Notification : " + id + "] " + message);
    }
}
