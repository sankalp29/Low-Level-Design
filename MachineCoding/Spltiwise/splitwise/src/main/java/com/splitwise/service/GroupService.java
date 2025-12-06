package com.splitwise.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.factory.SplitStrategyFactory;
import com.splitwise.model.Expense;
import com.splitwise.model.Group;
import com.splitwise.model.Split;
import com.splitwise.model.User;
import com.splitwise.repository.GroupRepository;
import com.splitwise.strategies.ISplitStrategy;

public class GroupService {
    private final UserService userService;
    private final GroupRepository groupRepository;

    // ------------------ GROUP OPERATIONS ------------------

    public String createGroup(String name) {
        Group group = new Group(name);
        groupRepository.saveGroup(group);
        // String id = group.getId();
        // groups.put(id, group);
        // balances.put(id, new HashMap<>());
        // groupMembers.put(id, new HashSet<>());
        // groupExpenses.put(id, new ArrayList<>());

        return group.getId();
    }

    public void addUser(String groupId, String userId) {
        groupRepository.addUser(groupId, userId);
        System.out.println(userId + " added to group " + groupId);
    }

    public boolean leaveUser(String groupId, String userId) {
        if (!isBalanceSettled(groupId, userId)) {
            System.out.println("Balance not settled. " + userId + " cannot leave group before settling balances");
            return false;
        }

        groupRepository.leaveUser(groupId, userId);
        System.out.println(userId + " left the group");
        return true;
    }

    // ------------------ EXPENSE OPERATIONS ------------------

    public void addExpense(String groupId,
                           String paidByUserId,
                           double amount,
                           String description,
                           List<String> users,
                           SplitType splitType,
                           List<Double> splitValues) throws UserNotFoundException {

        validateUsers(groupId, users);

        ISplitStrategy strategy = SplitStrategyFactory.getSplitStrategy(splitType);
        List<Split> splits = strategy.splitExpense(users, splitValues, amount);

        Expense expense = new Expense(paidByUserId, amount, description);
        groupRepository.addExpense(groupId, expense);
        updateBalances(groupId, paidByUserId, splits);
        notifyParticipants(groupId, users, expense);
    }

    public void settleBalance(String groupId, String senderId, String receiverId, double amount) throws UserNotFoundException {

        if (amount <= 0)
            throw new IllegalArgumentException("Settlement amount must be positive");
    
        // Map<String, Map<String, Double>> groupBalance = balances.get(groupId);
        Map<String, Map<String, Double>> groupBalance = groupRepository.getBalance(groupId);
    
        if (!groupBalance.containsKey(senderId) || !groupBalance.containsKey(receiverId))
            throw new IllegalArgumentException("Sender or receiver not part of group");
    
        // sender pays receiver → sender owes less, receiver gets back money
        double oldSenderOwes = groupBalance.get(senderId).getOrDefault(receiverId, 0.0);
        double oldReceiverOwes = groupBalance.get(receiverId).getOrDefault(senderId, 0.0);
    
        // Update pairwise balances
        groupBalance.get(senderId).put(receiverId, oldSenderOwes + amount);
        groupBalance.get(receiverId).put(senderId, oldReceiverOwes - amount);

        User receiver = userService.getUserById(receiverId);
        String notification = "Received Rs. " + amount + " from " + senderId;
        receiver.notify(notification);
    }
    
    private void updateBalances(String groupId, String paidBy, List<Split> splits) {    
        for (Split s : splits) {
            if (s.getUserId().equals(paidBy)) continue;
    
            String user = s.getUserId();
            double amt = s.getAmount();
    
            groupRepository.updateBalances(groupId, paidBy, user, amt);
        }
    }

    // ------------------ UTILITIES ------------------

    private boolean isBalanceSettled(String groupId, String userId) {
        Map<String, Map<String, Double>> balances = groupRepository.getBalance(groupId);
        for (double val : balances.get(userId).values()) {
            if (val != 0) return false;
        }
        return true;
    }

    private void validateUsers(String groupId, List<String> users) {
        Set<String> groupMembers = groupRepository.getGroupMembers(groupId);
        for (String u : users) {
            if (!groupMembers.contains(u))
                throw new IllegalArgumentException("User not in group: " + u);
        }
    }

    public void displayBalances(String groupId) {
        System.out.println(groupRepository.getBalance(groupId));
    }

    private void notifyParticipants(String groupId, List<String> users, Expense expense) throws UserNotFoundException {
        // Check all users exist in group
        validateUsers(groupId, users);

        // Send notification
        String expenseAdded = expense + " added in " + groupId;
        for (String userId : users) {
            User user = userService.getUserById(userId);
            user.notify(expenseAdded);
        }
    }

    public GroupService(UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
    }
}
