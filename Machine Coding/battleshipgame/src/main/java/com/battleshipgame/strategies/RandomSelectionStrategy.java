package com.battleshipgame.strategies;

import com.battleshipgame.Coordinate;

import java.util.Random;
import java.util.Set;

/**
 * Implements a random selection strategy for missile firing in the Battleship game.
 * This strategy ensures that missiles are fired at unique, random coordinates
 * within the opponent's designated battlefield half.
 */
public class RandomSelectionStrategy implements GameStrategy {

    /**
     * Generates a random, unfired coordinate within the opponent's half of the battlefield.
     * It continuously generates coordinates until a unique and valid one is found.
     * @param currentPlayer The ID of the player currently making a move.
     * @param n The size of the battlefield (N x N).
     * @param firedCoordinates A set of all coordinates that have already been fired upon.
     * @return A unique Coordinate for the next missile strike.
     * @throws IllegalStateException if it fails to find a unique coordinate (though highly unlikely in a practical game).
     */
    @Override
    public Coordinate makeMove(int currentPlayer, int n, Set<Coordinate> firedCoordinates) {
        Random random = new Random();
        int opponentPlayer = 1 - currentPlayer;
        int minCol, maxCol;

        if (opponentPlayer == 0) { // Opponent is Player A (left half)
            minCol = 0;
            maxCol = n / 2 - 1;
        } else { // Opponent is Player B (right half)
            minCol = n / 2;
            maxCol = n - 1;
        }

        Coordinate newMove;
        do {
            int randomX = random.nextInt(n);
            int randomY = random.nextInt(maxCol - minCol + 1) + minCol;
            newMove = new Coordinate(randomX, randomY);
        } while (firedCoordinates.contains(newMove));

        return newMove;
    }
}
