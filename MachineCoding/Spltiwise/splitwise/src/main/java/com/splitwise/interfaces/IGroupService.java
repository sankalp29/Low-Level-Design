package com.splitwise.interfaces;

import java.util.List;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;

public interface IGroupService {

    String createGroup(String name);

    void addUser(String groupId, String userId);

    boolean leaveUser(String groupId, String userId);

    void addExpense(String groupId,
            String paidByUserId,
            double amount,
            String description,
            List<String> users,
            SplitType splitType,
            List<Double> splitValues) throws UserNotFoundException;

    void settleBalance(String groupId, String senderId, String receiverId, double amount) throws UserNotFoundException;

    void simplify(String groupId);

    void displayBalances(String groupId);

}