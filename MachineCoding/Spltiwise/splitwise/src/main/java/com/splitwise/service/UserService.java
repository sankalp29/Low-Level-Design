package com.splitwise.service;

import java.util.List;

import com.splitwise.constants.SplitType;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.factory.SplitStrategyFactory;
import com.splitwise.interfaces.IUserRepository;
import com.splitwise.interfaces.IUserService;
import com.splitwise.model.Split;
import com.splitwise.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    
    @Override
    public String createUser(String name, String email) {
        User user = new User(name, email);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void updateUser(String userId, String name, String email) throws UserNotFoundException {
        User user = userRepository.getUserById(userId);
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        return userRepository.getUserById(userId);
    }

    @Override
    public void addExpense(String paidBy, String paidTo, Double amount, SplitType splitType, List<Double> userSplit) throws UserNotFoundException {
        User paidByUser = getUserById(paidBy);
        User paidToUser = getUserById(paidTo);
        List<Split> splits = SplitStrategyFactory.getSplitStrategy(splitType).splitExpense(List.of(paidBy, paidTo), userSplit, amount);

        paidByUser.addExpense(paidTo, splits.get(0).getAmount(), paidTo, splitType, userSplit);
        paidToUser.addExpense(paidBy, -splits.get(1).getAmount(), paidBy, splitType, userSplit);
    }
}
