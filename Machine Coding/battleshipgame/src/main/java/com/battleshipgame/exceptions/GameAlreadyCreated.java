package com.battleshipgame.exceptions;

/**
 * Exception thrown when an attempt is made to initialize a new game
 * when a game has already been created and is active.
 */
public class GameAlreadyCreated extends Exception {
    public GameAlreadyCreated(String message) {
        super(message);
    }
}
