package com.vendingmachine.exceptions;

public class IllegalCostException extends RuntimeException {
    public IllegalCostException(String message) {
        super(message);
    }
}
