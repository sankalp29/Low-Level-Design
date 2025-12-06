package com.splitwise.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.splitwise.model.Expense;
import com.splitwise.model.Group;

public class GroupRepository {
    private final Map<String, Group> groups;
    private final Map<String, Map<String, Map<String, Double>>> balances;
    private final Map<String, Set<String>> groupMembers;
    private final Map<String, List<Expense>> groupExpenses;

    public void saveGroup(Group group) {
        String id = group.getId();
        groups.put(id, group);
        balances.put(id, new HashMap<>());
        groupMembers.put(id, new HashSet<>());
        groupExpenses.put(id, new ArrayList<>());
    }

    public void addUser(String groupId, String userId) {
        groupMembers.get(groupId).add(userId);
        balances.get(groupId).putIfAbsent(userId, new HashMap<>());

        for (String other : groupMembers.get(groupId)) {
            if (userId.equals(other)) continue;
            balances.get(groupId).get(userId).putIfAbsent(other, 0.0);
            balances.get(groupId).get(other).putIfAbsent(userId, 0.0);
        }
    }

    public void leaveUser(String groupId, String userId) {
        groupMembers.get(groupId).remove(userId);
        balances.get(groupId).remove(userId);
        Map<String, Map<String, Double>> groupBalancesMap = balances.get(groupId);
        for (Map.Entry<String, Map<String, Double>> entry : groupBalancesMap.entrySet()) {
            if (entry.getKey().equals(userId)) continue;
            if (entry.getValue().containsKey(userId)) entry.getValue().remove(userId);
        }
    }

    public void addExpense(String groupId, Expense expense) {
        groupExpenses.get(groupId).add(expense);
    }

    public void updateBalances(String groupId, String paidBy, String userId, Double amount) {
        Map<String, Map<String, Double>> groupBalance = balances.get(groupId);

        // user owes paidBy
        groupBalance.get(paidBy).put(userId, 
            groupBalance.get(paidBy).get(userId) + amount);

        groupBalance.get(userId).put(paidBy,
            groupBalance.get(userId).get(paidBy) - amount);
    
    }

    public Map<String, Map<String, Double>> getBalance(String groupId) {
        return balances.get(groupId);
    }

    public Set<String> getGroupMembers(String groupId) {
        return groupMembers.get(groupId);
    }

    public GroupRepository() {
        this.groups = new HashMap<>();
        this.balances = new HashMap<>();
        this.groupMembers = new HashMap<>();
        this.groupExpenses = new HashMap<>();
    }
}
