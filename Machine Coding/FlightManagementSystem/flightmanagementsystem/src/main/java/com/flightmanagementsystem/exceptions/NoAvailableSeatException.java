package com.flightmanagementsystem.exceptions;

public class NoAvailableSeatException extends Exception {
    public NoAvailableSeatException(String message) {
        super(message);
    }
}
