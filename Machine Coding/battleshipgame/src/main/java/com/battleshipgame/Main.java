package com.battleshipgame;

import com.battleshipgame.exceptions.CoordinatesAlreadyOccupiedByShip;
import com.battleshipgame.exceptions.GameAlreadyCreated;
import com.battleshipgame.exceptions.GameAlreadyRunningException;
import com.battleshipgame.exceptions.InvalidCoordinatesSpecifiedException;
import com.battleshipgame.exceptions.UnequalShipsCountException;
import com.battleshipgame.strategies.RandomSelectionStrategy;

public class Main {
    public static void main(String[] args) {
        // Uncomment the test you want to run
        // testUnequalShipsStarter();
        // testOverlappingShipsAddition();
        testFairGame();
    }

    private static void testUnequalShipsStarter() {
        BattleShipManager battleShipManager = new BattleShipManager();
        try {
            battleShipManager.initGame(6, new RandomSelectionStrategy());
            System.out.println("New game initiated");
        } catch (GameAlreadyCreated ex) {
            System.out.println(ex.getMessage());
        }
        
        try {
            battleShipManager.addShip(0, 2, new Coordinate(0, 0));
            System.out.println("New ship added for A");
        } catch (InvalidCoordinatesSpecifiedException | CoordinatesAlreadyOccupiedByShip e) {
            e.printStackTrace();
        }

        try {
            battleShipManager.startGame();
        } catch (UnequalShipsCountException | GameAlreadyRunningException e) {
            e.printStackTrace();
        }
    }

    private static void testOverlappingShipsAddition() {
        BattleShipManager battleShipManager = new BattleShipManager();
        try {
            battleShipManager.initGame(6, new RandomSelectionStrategy());
            System.out.println("New game initiated");
        } catch (GameAlreadyCreated ex) {
            System.out.println(ex.getMessage());
        }

        try {
            battleShipManager.addShip(0, 2, new Coordinate(0, 0));
            System.out.println("New ship added for A");
            System.out.println();
            battleShipManager.viewBattleField();
            System.out.println();
            // This should now throw CoordinatesAlreadyOccupiedByShip or InvalidCoordinatesSpecifiedException due to half-field validation
            battleShipManager.addShip(0, 2, new Coordinate(1, 1)); // This ship would overlap with (0,0) (size 2) if in same player field
            System.out.println("Adding 2nd ship for A");
        } catch (InvalidCoordinatesSpecifiedException | CoordinatesAlreadyOccupiedByShip e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    private static void testFairGame() {
        BattleShipManager battleShipManager = new BattleShipManager();
        try {
            battleShipManager.initGame(6, new RandomSelectionStrategy());
            System.out.println("New game initiated");
        } catch (GameAlreadyCreated ex) {
            System.out.println(ex.getMessage());
        }

        try {
            // Player A ships (left half, col < 3)
            battleShipManager.addShip(0, 2, new Coordinate(0, 0));
            battleShipManager.addShip(0, 2, new Coordinate(3, 0));
            
            // Player B ships (right half, col >= 3)
            battleShipManager.addShip(1, 3, new Coordinate(0, 3)); // Example: Top-left at (0,3), size 3. Occupies (0,3) (0,4) (0,5), (1,3)...(2,5)
            battleShipManager.addShip(1, 2, new Coordinate(3, 3));

            battleShipManager.startGame();
            battleShipManager.viewBattleField(); // View final state
        } catch (InvalidCoordinatesSpecifiedException | CoordinatesAlreadyOccupiedByShip | UnequalShipsCountException | GameAlreadyRunningException e) {
            e.printStackTrace();
        }
    }
}