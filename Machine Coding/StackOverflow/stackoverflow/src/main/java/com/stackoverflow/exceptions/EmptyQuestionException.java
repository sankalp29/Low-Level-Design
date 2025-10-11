package com.stackoverflow.exceptions;

public class EmptyQuestionException extends Exception {
    public EmptyQuestionException(String message) {
        super(message);
    }
}
