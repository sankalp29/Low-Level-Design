package com.parkinglot.exception;

public class SlotAlreadyFreeException extends Exception {
    public SlotAlreadyFreeException(String message) {
        super(message);
    }
}
