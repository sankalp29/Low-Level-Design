package com.splitwise.constants;

public class ExceptionConstants {
    public static final String USER_NOT_FOUND_EXCEPTION = "User not found. Invalid userId"; 
    public static final String USER_DOES_NOT_EXIST_IN_GROUP_EXCEPTION = "User is not part of the group.";
    public static final String ACCOUNT_BALANCE_UNSETTLED_EXCEPTION = "Account balance is not settled.";
    public static final String EXPENSE_NOT_FOUND_EXCEPTION = "Expense with id not found.";
    public static final String INVALID_USER_SPLIT_EXCEPTION = "Invalid split specified by user. Individual splits do not add up to the total specified.";
    
    private ExceptionConstants() {}
}
