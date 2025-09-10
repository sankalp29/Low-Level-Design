package com.battleshipgame.exceptions;

/**
 * Exception thrown when coordinates specified for a ship placement or missile fire
 * are invalid, such as being out of bounds or in the wrong battlefield half.
 */
public class InvalidCoordinatesSpecifiedException extends Exception {
    public InvalidCoordinatesSpecifiedException(String message) {
        super(message);
    }
}
