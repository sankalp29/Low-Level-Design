package com.battleshipgame.strategies;

import com.battleshipgame.Coordinate;

import java.util.Set;

/**
 * Defines the interface for a game strategy, specifically for determining
 * the next missile firing coordinate in the Battleship game.
 * New firing strategies can implement this interface.
 */
public interface GameStrategy {
    /**
     * Determines the next coordinate for a missile strike.
     * The strategy must ensure the returned coordinate is within the opponent's
     * battlefield half and has not been fired upon previously.
     * @param currentPlayer The ID of the player currently making a move (0 for Player A, 1 for Player B).
     * @param n The size of the battlefield (N x N).
     * @param firedCoordinates A set of all coordinates that have already been fired upon in the game.
     * @return A Coordinate representing the target for the missile strike.
     */
    public Coordinate makeMove(int currentPlayer, int n, Set<Coordinate> firedCoordinates);
}
