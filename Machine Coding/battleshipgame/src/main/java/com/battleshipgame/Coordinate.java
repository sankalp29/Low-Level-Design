package com.battleshipgame;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a single coordinate (X, Y) on the Battleship game board.
 * This class is immutable and provides proper equals and hashCode implementations
 * for use in collections like Sets and Maps.
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Coordinate {
    private final int x;
    private final int y;
}
