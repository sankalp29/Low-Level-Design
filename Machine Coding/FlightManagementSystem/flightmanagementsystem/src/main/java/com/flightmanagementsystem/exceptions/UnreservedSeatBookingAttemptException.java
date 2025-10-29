package com.flightmanagementsystem.exceptions;

public class UnreservedSeatBookingAttemptException extends Exception {
    public UnreservedSeatBookingAttemptException(String message) {
        super(message);
    }
}
