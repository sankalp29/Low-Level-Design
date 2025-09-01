package com.librarymanagement.exceptions;

public class BookIssueLimitReachedException extends Exception {
    public BookIssueLimitReachedException(String message) {
        super(message);
    }
}
