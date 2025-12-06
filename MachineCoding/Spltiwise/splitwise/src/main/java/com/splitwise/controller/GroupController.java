package com.splitwise.controller;

import java.util.List;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.service.GroupService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    public String createGroup(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Group name cannot be empty");
        return groupService.createGroup(name);
    }

    public void addUser(String groupId, String userId) {
        groupService.addUser(groupId, userId);
    }

    public boolean leaveUser(String groupId, String userId) {
        return groupService.leaveUser(groupId, userId);
    }

    public void addExpense(String groupId,
                           String paidByUserId,
                           double amount,
                           String description,
                           List<String> users,
                           SplitType splitType,
                           List<Double> splitValues) throws UserNotFoundException {

        groupService.addExpense(groupId, paidByUserId, amount, description, users, splitType, splitValues);
    }

    public void settleBalance(String groupId, String senderId, String receiverId, double amount) throws UserNotFoundException {
        if (amount <= 0)
            throw new IllegalArgumentException("Settlement amount must be positive");
    
        groupService.settleBalance(groupId, senderId, receiverId, amount);
    }

    public void displayBalances(String groupId) {
        groupService.displayBalances(groupId);
    }
}
