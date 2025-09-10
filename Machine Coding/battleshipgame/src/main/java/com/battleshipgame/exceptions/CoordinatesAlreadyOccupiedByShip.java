package com.battleshipgame.exceptions;

/**
 * Exception thrown when a player attempts to place a ship on coordinates
 * that are already occupied by another ship.
 */
public class CoordinatesAlreadyOccupiedByShip extends Exception {
    public CoordinatesAlreadyOccupiedByShip(String message) {
        super(message);
    }
}
