package com.battleshipgame.exceptions;

/**
 * Exception thrown when an attempt is made to start a game
 * that is already in progress.
 */
public class GameAlreadyRunningException extends Exception {
    public GameAlreadyRunningException(String message) {
        super(message);
    }
}
