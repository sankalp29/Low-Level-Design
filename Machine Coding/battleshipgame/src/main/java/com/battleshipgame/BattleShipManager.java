package com.battleshipgame;

import com.battleshipgame.exceptions.CoordinatesAlreadyOccupiedByShip;
import com.battleshipgame.exceptions.GameAlreadyCreated;
import com.battleshipgame.exceptions.GameAlreadyRunningException;
import com.battleshipgame.exceptions.InvalidCoordinatesSpecifiedException;
import com.battleshipgame.exceptions.UnequalShipsCountException;
import com.battleshipgame.strategies.GameStrategy;

/**
 * Manages the overall flow and state of the Battleship game.
 * This class acts as the service layer, orchestrating interactions between the game board,
 * game rules helper, and the firing strategy.
 */
public class BattleShipManager {
    private Integer n;
    private BattleShipGame battleShipGame;
    private GameStrategy gameStrategy;
    private BattleShipGameHelper battleShipGameHelper;
    private int nextShipIdA = 1;
    private int nextShipIdB = 1;

    /**
     * Initializes a new Battleship game with a specified board size and firing strategy.
     * @param n The size of the square battlefield (N x N).
     * @param gameStrategy The strategy to be used for making moves (e.g., random, AI).
     * @return The initialized BattleShipGame instance.
     * @throws GameAlreadyCreated If a game has already been initialized.
     */
    public BattleShipGame initGame(Integer n, GameStrategy gameStrategy) throws GameAlreadyCreated {
        if (battleShipGame != null) {
            throw new GameAlreadyCreated("Game already exists exception");
        }
        this.n = n;
        this.battleShipGame = new BattleShipGame(n);
        this.gameStrategy = gameStrategy;
        this.battleShipGameHelper = new BattleShipGameHelper();
        this.nextShipIdA = 1;
        this.nextShipIdB = 1;
        return battleShipGame;
    }

    /**
     * Adds a ship to a player's fleet at a specified top-left coordinate with a given size.
     * Performs extensive validation to ensure the ship is placed within the player's allocated
     * battlefield half, is within bounds, and does not overlap with existing ships.
     * @param player The ID of the player (0 for Player A, 1 for Player B).
     * @param size The size (side length) of the square ship.
     * @param topLeftCoordinate The top-left Coordinate of the ship.
     * @throws InvalidCoordinatesSpecifiedException If the placement is out of bounds, outside the player's half, or too large.
     * @throws CoordinatesAlreadyOccupiedByShip If any part of the ship's intended placement is already occupied.
     */
    public void addShip(int player, int size, Coordinate topLeftCoordinate) throws InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        if (!battleShipGameHelper.isPlacementValidForPlayer(player, topLeftCoordinate, size, n)) {
            throw new InvalidCoordinatesSpecifiedException("Ship placement is not valid for player's allocated half.");
        }

        for (int i = topLeftCoordinate.getX(); i < topLeftCoordinate.getX() + size; i++) {
            for (int j = topLeftCoordinate.getY(); j < topLeftCoordinate.getY() + size; j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                if (!battleShipGameHelper.isValid(currentCoordinate, n)) {
                    throw new InvalidCoordinatesSpecifiedException("Ship goes out of bounds at " + currentCoordinate);
                }
                if (battleShipGame.isOccupied(currentCoordinate)) {
                    throw new CoordinatesAlreadyOccupiedByShip("The coordinate " + currentCoordinate + " is already occupied by a ship.");
                }
            }
        }

        String shipId = generateShipId(player);
        ShipInfo ship = new ShipInfo(player, shipId, topLeftCoordinate, size);

        for (int i = topLeftCoordinate.getX(); i < topLeftCoordinate.getX() + size; i++) {
            for (int j = topLeftCoordinate.getY(); j < topLeftCoordinate.getY() + size; j++) {
                battleShipGame.setCoordinate(new Coordinate(i, j), ship);
            }
        }
        if (player == 0) battleShipGame.incrementShipsACount();
        else battleShipGame.incrementShipsBCount();
    }

    /**
     * Initiates and runs the Battleship game simulation. Player A always takes the first turn.
     * The game continues until one player has no ships remaining.
     * Outputs the results of each missile fire (hit or miss) to the console.
     * @throws UnequalShipsCountException If players do not have an equal number of ships before starting.
     * @throws GameAlreadyRunningException If the game has already been started.
     */
    public void startGame() throws UnequalShipsCountException, GameAlreadyRunningException {
        if (!battleShipGameHelper.isShipCountEqual(battleShipGame)) {
            throw new UnequalShipsCountException("Both players should have equal number of ships to start game.");
        }
        if (battleShipGame.isGameStarted()) {
            throw new GameAlreadyRunningException("The game is already running...");
        }

        System.out.println("Starting game....");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        battleShipGame.setGameStarted();
        System.out.println("Started!");

        simulateGame();
    }

    /**
     * Simulates the turns of the game, with players firing missiles until one player wins.
     * Outputs game events to the console.
     */
    private void simulateGame() {
        int turn = 0; // Player 0 starts
        while (battleShipGame.getShipsACount() > 0 && battleShipGame.getShipsBCount() > 0) {
            Coordinate missileTarget = gameStrategy.makeMove(turn, n, battleShipGame.getFiredCoordinates());
            battleShipGame.addFiredCoordinate(missileTarget);

            String playerTurnMessage = (turn == 0) ? "PlayerA’s turn" : "PlayerB’s turn";
            System.out.print(playerTurnMessage + ": Missile fired at (" + missileTarget.getX() + ", " + missileTarget.getY() + "). ");

            int opponentPlayer = 1 - turn;
            if (battleShipGameHelper.isIntercepting(battleShipGame, opponentPlayer, missileTarget)) {
                System.out.print("Hit. ");
                destroyShip(missileTarget);
            } else {
                System.out.println("Miss.");
            }
            
            turn = 1 - turn;
        }

        if (battleShipGame.getShipsACount() > 0) System.out.println("Player A Wins!");
        else System.out.println("Player B Wins!");
    }

    /**
     * Displays the current state of the battlefield grid to the console.
     * Each cell shows the ship ID (e.g., A-SH1, B-SH2) if occupied, or "." if empty.
     */
    public void viewBattleField() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                ShipInfo ship = battleShipGame.getGrid().get(currentCoordinate);
                if (ship == null) {
                    System.out.print(".    ");
                } else {
                    System.out.print(ship.getShipId() + "    ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Destroys a ship at a given coordinate by removing all its occupied cells from the grid.
     * Updates the ship count for the affected player and outputs a destruction message.
     * @param coordinate The Coordinate where the ship was hit.
     */
    private void destroyShip(Coordinate coordinate) {
        ShipInfo ship = battleShipGame.getShipAt(coordinate);
        if (ship == null) {
            return; // Should not happen if isIntercepting is true, but for safety
        }
        Coordinate topLeft = ship.getTopLeftCoordinate();
        int size = ship.getSize();
        String shipId = ship.getShipId();
        int player = ship.getPlayer();

        for (int i = topLeft.getX(); i < topLeft.getX() + size; i++) {
            for (int j = topLeft.getY(); j < topLeft.getY() + size; j++) {
                battleShipGame.destroyShip(new Coordinate(i, j));
            }
        }

        if (player == 0) {
            System.out.println("Player A's ship with ID : " + shipId + " destroyed.");
            battleShipGame.decrementShipsACount();
        } else {
            System.out.println("Player B's ship with ID : " + shipId + " destroyed.");
            battleShipGame.decrementShipsBCount();
        }
    }

    /**
     * Generates a unique ship ID for a given player.
     * @param player The ID of the player (0 for Player A, 1 for Player B).
     * @return A unique ship ID string (e.g., "A-SH1", "B-SH5").
     */
    private String generateShipId(int player) {
        if (player == 0) {
            return "A-SH" + (nextShipIdA++);
        } else {
            return "B-SH" + (nextShipIdB++);
        }
    }
}