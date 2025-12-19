package com.irctcsystem.exceptions;

public class DuplicateBookingRequestException extends RuntimeException {
    public DuplicateBookingRequestException(String message) {
        super(message);
    }
}
