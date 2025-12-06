package com.splitwise.exceptions;

public class UserDoesNotExistInGroup extends Exception {
    public UserDoesNotExistInGroup(String message) {
        super(message);
    }
}
