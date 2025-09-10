package com.battleshipgame;

/**
 * Helper class providing utility methods for game rules and validations
 * related to the Battleship game.
 */
public class BattleShipGameHelper {

    /**
     * Checks if a given coordinate is within the bounds of the N x N battlefield.
     * @param coordinate The Coordinate to validate.
     * @param n The size of the battlefield (N).
     * @return true if the coordinate is valid, false otherwise.
     */
    public boolean isValid(Coordinate coordinate, int n) {
        return coordinate.getX() >= 0 && coordinate.getX() < n && coordinate.getY() >= 0 && coordinate.getY() < n;
    }

    /**
     * Determines if a missile fired at a given coordinate lands within the current player's own half
     * of the battlefield. This is used to ensure players only fire at the opponent's field.
     * @param player The ID of the current player (0 for Player A, 1 for Player B).
     * @param coordinate The Coordinate of the missile target.
     * @param n The size of the battlefield (N).
     * @return true if the coordinate is in the current player's half, false otherwise.
     */
    public boolean isInSelf(int player, Coordinate coordinate, int n) {
        int col = coordinate.getY();
        return (player == 0) ? col < n / 2 : col >= n / 2;
    }

    /**
     * Checks if a missile fired at a given coordinate intercepts an opponent's ship.
     * @param battleShipGame The current BattleShipGame instance.
     * @param opponentPlayer The ID of the opponent player.
     * @param coordinate The Coordinate where the missile was fired.
     * @return true if an opponent's ship is at the target coordinate, false otherwise.
     */
    public boolean isIntercepting(BattleShipGame battleShipGame, int opponentPlayer, Coordinate coordinate) {
        ShipInfo ship = battleShipGame.getGrid().get(coordinate);
        return ship != null && ship.getPlayer() == opponentPlayer;
    }

    /**
     * Checks if both players have an equal number of ships.
     * This is a pre-condition for starting the game.
     * @param battleShipGame The current BattleShipGame instance.
     * @return true if ship counts are equal, false otherwise.
     */
    public boolean isShipCountEqual(BattleShipGame battleShipGame) {
        return battleShipGame.getShipsACount() == battleShipGame.getShipsBCount();
    }

    /**
     * Validates if a ship can be placed at the given top-left coordinate for a specific player,
     * respecting the battlefield's half-split rule.
     * @param player The ID of the player attempting to place the ship.
     * @param topLeft The top-left Coordinate of the ship.
     * @param size The size (side length) of the square ship.
     * @param n The size of the battlefield (N).
     * @return true if the placement is valid for the player's allocated half, false otherwise.
     */
    public boolean isPlacementValidForPlayer(int player, Coordinate topLeft, int size, int n) {
        int startCol = topLeft.getY();
        int endCol = topLeft.getY() + size - 1;

        if (player == 0) { // Player A (left half)
            return endCol < n / 2;
        } else { // Player B (right half)
            return startCol >= n / 2;
        }
    }
}
