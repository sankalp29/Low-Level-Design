package com.splitwise.interfaces;

import java.util.Map;
import java.util.Set;

import com.splitwise.model.Expense;
import com.splitwise.model.Group;

public interface IGroupRepository {

    void saveGroup(Group group);

    void addUser(String groupId, String userId);

    void leaveUser(String groupId, String userId);

    void addExpense(String groupId, Expense expense);

    void updateBalances(String groupId, String paidBy, String userId, Double amount);

    Map<String, Map<String, Double>> getBalance(String groupId);

    Set<String> getGroupMembers(String groupId);

    void saveGroupBalance(String groupId, Map<String, Map<String, Double>> balance);

}