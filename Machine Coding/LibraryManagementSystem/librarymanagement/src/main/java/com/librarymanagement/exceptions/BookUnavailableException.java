package com.librarymanagement.exceptions;

public class BookUnavailableException extends Exception {
    public BookUnavailableException(String message) {
        super(message);
    }
}
