package com.splitwise;

import java.util.ArrayList;
import java.util.List;

import com.splitwise.constants.SplitType;
import com.splitwise.controller.GroupController;
import com.splitwise.controller.UserController;
import com.splitwise.exceptions.UserNotFoundException;
import com.splitwise.repository.GroupRepository;
import com.splitwise.repository.UserRepository;
import com.splitwise.service.GroupService;
import com.splitwise.service.UserService;

public class Main {
    public static void main(String[] args) throws UserNotFoundException {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        GroupRepository groupRepository = new GroupRepository();
        GroupService groupService = new GroupService(userService, groupRepository);
        GroupController groupController = new GroupController(groupService);

        String sankalp = userController.createUser("Sankalp", "sankalp@gmail.com");
        String janvi = userController.createUser("Janvi", "janvi@gmail.com");
        String friend1 = userController.createUser("Friend1", "friend1@gmail.com");
        String friend2 = userController.createUser("Friend2", "friend2@gmail.com");

        String groupId = groupController.createGroup("Trip");
        groupController.addUser(groupId, sankalp);
        groupController.addUser(groupId, janvi);
        groupController.addUser(groupId, friend1);
        System.out.println();

        List<String> lunch = List.of(sankalp, janvi, friend1);
        groupController.addExpense(groupId, sankalp, 1200.0, "Lunch", lunch, SplitType.EQUAL, new ArrayList<>());
        System.out.println();
        System.out.println("======== Group Balances after Lunch ========");
        groupController.displayBalances(groupId);
        System.out.println();

        List<String> dinner = List.of(sankalp, janvi, friend1);
        List<Double> split = List.of(50.0, 25.0, 25.0);
        groupController.addExpense(groupId, janvi, 2100.0, "Dinner", dinner, SplitType.PERCENTAGE, split);
        System.out.println();

        System.out.println("======== Group Balances after Dinner ========");
        groupController.displayBalances(groupId);
        System.out.println();

        System.out.println("======== Exit attempt : User-3 ========");
        try {
            groupController.leaveUser(groupId, friend1);
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("======== Settling balance : User-3 ========");
        groupController.settleBalance(groupId, friend1, sankalp, 400.0);
        groupController.settleBalance(groupId, friend1, janvi, 525.0);
        System.out.println();

        System.out.println("======== Exit attempt again : User-3 ========");
        try {
            groupController.leaveUser(groupId, friend1);
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        groupController.displayBalances(groupId);
    }
}