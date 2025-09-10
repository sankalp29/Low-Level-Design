package com.battleshipgame;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents the core game board and state for the Battleship game.
 * Manages the grid where ships are placed, tracks fired coordinates,
 * and maintains counts of active ships for both players.
 */
@Getter
public class BattleShipGame {
    private final Integer n;
    private final Map<Coordinate, ShipInfo> grid;
    private final Set<Coordinate> firedCoordinates;
    private boolean gameStarted;
    private Integer shipsACount, shipsBCount;

    /**
     * Initializes a new Battleship game board.
     * @param n The size of the square battlefield (N x N).
     */
    public BattleShipGame(Integer n) {
        this.n = n;
        this.grid = new HashMap<>();
        this.firedCoordinates = new HashSet<>();
        this.gameStarted = false;
        this.shipsACount = 0;
        this.shipsBCount = 0;
    }

    /**
     * Places a ship at a specific coordinate on the grid.
     * @param coordinate The Coordinate where the ship is to be placed.
     * @param ship The ShipInfo object representing the ship.
     */
    public void setCoordinate(Coordinate coordinate, ShipInfo ship) {
        grid.put(coordinate, ship);
    }

    /**
     * Removes a ship (or part of a ship) from a specific coordinate on the grid,
     * effectively destroying that part of the ship.
     * @param coordinate The Coordinate from which the ship is to be removed.
     */
    public void destroyShip(Coordinate coordinate) {
        grid.remove(coordinate);
    }

    /**
     * Increments the count of active ships for Player A.
     * @return The new count of ships for Player A.
     */
    public Integer incrementShipsACount() {
        return ++shipsACount;
    }

    /**
     * Increments the count of active ships for Player B.
     * @return The new count of ships for Player B.
     */
    public Integer incrementShipsBCount() {
        return ++shipsBCount;
    }

    /**
     * Decrements the count of active ships for Player A.
     * @return The new count of ships for Player A.
     */
    public Integer decrementShipsACount() {
        return --shipsACount;
    }

    /**
     * Decrements the count of active ships for Player B.
     * @return The new count of ships for Player B.
     */
    public Integer decrementShipsBCount() {
        return --shipsBCount;
    }

    /**
     * Sets the game status to started.
     */
    public void setGameStarted() {
        this.gameStarted = true;
    }

    /**
     * Checks if a specific coordinate on the grid is occupied by a ship.
     * @param coordinate The Coordinate to check.
     * @return true if the coordinate is occupied, false otherwise.
     */
    public boolean isOccupied(Coordinate coordinate) {
        return grid.containsKey(coordinate);
    }

    /**
     * Adds a coordinate to the set of fired coordinates.
     * @param coordinate The Coordinate that has been fired upon.
     */
    public void addFiredCoordinate(Coordinate coordinate) {
        firedCoordinates.add(coordinate);
    }

    /**
     * Checks if a specific coordinate has already been fired upon.
     * @param coordinate The Coordinate to check.
     * @return true if the coordinate has been fired, false otherwise.
     */
    public boolean isCoordinateFired(Coordinate coordinate) {
        return firedCoordinates.contains(coordinate);
    }

    /**
     * Retrieves the ShipInfo object at a given coordinate.
     * @param coordinate The Coordinate to retrieve the ship from.
     * @return The ShipInfo object at the coordinate, or null if no ship is present.
     */
    public ShipInfo getShipAt(Coordinate coordinate) {
        return grid.get(coordinate);
    }
}
