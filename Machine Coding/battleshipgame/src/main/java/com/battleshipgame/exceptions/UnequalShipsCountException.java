package com.battleshipgame.exceptions;

/**
 * Exception thrown when an attempt is made to start the game
 * with an unequal number of ships between Player A and Player B.
 */
public class UnequalShipsCountException extends Exception {
    public UnequalShipsCountException(String message) {
        super(message);
    }
}
