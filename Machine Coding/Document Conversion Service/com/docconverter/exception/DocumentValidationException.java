package com.docconverter.exception;

public class DocumentValidationException extends Exception {
    public DocumentValidationException(String message) {
        super(message);
    }

    public DocumentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
