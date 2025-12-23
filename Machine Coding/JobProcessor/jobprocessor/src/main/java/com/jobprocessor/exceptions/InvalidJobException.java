package com.jobprocessor.exceptions;

public class InvalidJobException extends RuntimeException {
    public InvalidJobException(String message) {
        super(message);
    }
}
