package com.battleshipgame;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a single ship in the Battleship game.
 * Stores information about the ship's owner, unique ID, its top-left coordinate,
 * and its size (since ships are square).
 * This class is immutable.
 */
@AllArgsConstructor
@Getter
public class ShipInfo {
    private final Integer player;
    private final String shipId;
    private final Coordinate topLeftCoordinate;
    private final Integer size;
}
