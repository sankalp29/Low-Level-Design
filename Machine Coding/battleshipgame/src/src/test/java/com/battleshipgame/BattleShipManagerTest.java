package com.battleshipgame;

import com.battleshipgame.exceptions.*;
import com.battleshipgame.strategies.GameStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BattleShipManagerTest {

    private BattleShipManager battleShipManager;
    private GameStrategy mockGameStrategy;
    private int N = 6;

    @BeforeEach
    void setUp() throws GameAlreadyCreated {
        battleShipManager = new BattleShipManager();
        mockGameStrategy = new MockRandomSelectionStrategy(); // Use a mock for controlled testing
        battleShipManager.initGame(N, mockGameStrategy);
    }

    // Mock strategy for controlled testing
    static class MockRandomSelectionStrategy implements GameStrategy {
        private Coordinate[] predefinedMoves;
        private int moveIndex = 0;

        public void setPredefinedMoves(Coordinate... moves) {
            this.predefinedMoves = moves;
            this.moveIndex = 0;
        }

        @Override
        public Coordinate makeMove(int currentPlayer, int n, Set<Coordinate> firedCoordinates) {
            if (predefinedMoves != null && moveIndex < predefinedMoves.length) {
                Coordinate nextMove = predefinedMoves[moveIndex++];

                return nextMove;
            }
            throw new IllegalStateException("No predefined moves left or moves not set.");
        }
    }

    @Test
    void testInitGame() {
        assertNotNull(battleShipManager);
        assertNotNull(battleShipManager.getBattleShipGame());
        assertFalse(battleShipManager.getBattleShipGame().isGameStarted());
        assertEquals(0, battleShipManager.getBattleShipGame().getShipsACount());
        assertEquals(0, battleShipManager.getBattleShipGame().getShipsBCount());
    }

    @Test
    void testInitGameAlreadyCreated() throws GameAlreadyCreated {
        assertThrows(GameAlreadyCreated.class, () -> battleShipManager.initGame(N, mockGameStrategy));
    }

    @Test
    void testAddShipValidPlacementPlayerA() throws InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        battleShipManager.addShip(0, 2, new Coordinate(0, 0));
        assertEquals(1, battleShipManager.getBattleShipGame().getShipsACount());
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(0, 0)));
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(0, 1)));
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(1, 0)));
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(1, 1)));
    }

    @Test
    void testAddShipInvalidPlacementPlayerAOutsideHalf() {
        assertThrows(InvalidCoordinatesSpecifiedException.class, () -> battleShipManager.addShip(0, 2, new Coordinate(0, 3))); // Col 3 is B's half for N=6
    }

    @Test
    void testAddShipValidPlacementPlayerB() throws InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        battleShipManager.addShip(1, 2, new Coordinate(0, 3)); // Col 3 is B's half for N=6
        assertEquals(1, battleShipManager.getBattleShipGame().getShipsBCount());
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(0, 3)));
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(0, 4)));
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(1, 3)));
        assertNotNull(battleShipManager.getBattleShipGame().getShipAt(new Coordinate(1, 4)));
    }

    @Test
    void testAddShipInvalidPlacementPlayerBOutsideHalf() {
        assertThrows(InvalidCoordinatesSpecifiedException.class, () -> battleShipManager.addShip(1, 2, new Coordinate(0, 0))); // Col 0 is A's half for N=6
    }

    @Test
    void testAddShipOutOfBounds() {
        assertThrows(InvalidCoordinatesSpecifiedException.class, () -> battleShipManager.addShip(0, 2, new Coordinate(5, 0))); // x+size out of bounds
        assertThrows(InvalidCoordinatesSpecifiedException.class, () -> battleShipManager.addShip(0, 2, new Coordinate(0, 2))); // y+size out of bounds for A's half
    }

    @Test
    void testAddShipOverlap() throws InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        battleShipManager.addShip(0, 2, new Coordinate(0, 0));
        assertThrows(CoordinatesAlreadyOccupiedByShip.class, () -> battleShipManager.addShip(0, 2, new Coordinate(1, 1)));
    }

    @Test
    void testStartGameUnequalShips() throws GameAlreadyCreated, InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        battleShipManager.addShip(0, 2, new Coordinate(0, 0));
        assertThrows(UnequalShipsCountException.class, () -> battleShipManager.startGame());
    }

    @Test
    void testStartGameAlreadyRunning() throws GameAlreadyCreated, UnequalShipsCountException, GameAlreadyRunningException, InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        battleShipManager.addShip(0, 2, new Coordinate(0, 0));
        battleShipManager.addShip(1, 2, new Coordinate(0, 3));
        battleShipManager.startGame();
        assertThrows(GameAlreadyRunningException.class, () -> battleShipManager.startGame());
    }

    @Test
    void testGameFlowHitAndMiss() throws InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip, UnequalShipsCountException, GameAlreadyRunningException {
        // Player A ships
        battleShipManager.addShip(0, 1, new Coordinate(0, 0)); // A-SH1
        battleShipManager.addShip(0, 1, new Coordinate(1, 0)); // A-SH2

        // Player B ships
        battleShipManager.addShip(1, 1, new Coordinate(0, 3)); // B-SH1
        battleShipManager.addShip(1, 1, new Coordinate(1, 3)); // B-SH2

        // Set predefined moves for PlayerA (hitting B) and PlayerB (missing A, then hitting A)
        ((MockRandomSelectionStrategy) mockGameStrategy).setPredefinedMoves(
                new Coordinate(0, 3), // PlayerA hits B-SH1
                new Coordinate(5, 0), // PlayerB misses A
                new Coordinate(1, 0)  // PlayerB hits A-SH2
        );

        battleShipManager.startGame();

        // After game, check final counts
        assertEquals(1, battleShipManager.getBattleShipGame().getShipsACount()); // A-SH1 remaining
        assertEquals(1, battleShipManager.getBattleShipGame().getShipsBCount()); // B-SH2 remaining (B-SH1 destroyed by A)

        // Re-run with more moves to end game
        setUp(); // Re-initialize game
        battleShipManager.addShip(0, 1, new Coordinate(0, 0)); // A-SH1
        battleShipManager.addShip(0, 1, new Coordinate(1, 0)); // A-SH2
        battleShipManager.addShip(1, 1, new Coordinate(0, 3)); // B-SH1
        battleShipManager.addShip(1, 1, new Coordinate(1, 3)); // B-SH2

        ((MockRandomSelectionStrategy) mockGameStrategy).setPredefinedMoves(
                new Coordinate(0, 3), // PlayerA hits B-SH1
                new Coordinate(5, 0), // PlayerB misses A
                new Coordinate(1, 3), // PlayerA hits B-SH2
                new Coordinate(2, 0)  // PlayerB misses A
        );

        battleShipManager.startGame();
        assertEquals(2, battleShipManager.getBattleShipGame().getShipsACount());
        assertEquals(0, battleShipManager.getBattleShipGame().getShipsBCount());
    }

    @Test
    void testViewBattleField() throws InvalidCoordinatesSpecifiedException, CoordinatesAlreadyOccupiedByShip {
        battleShipManager.addShip(0, 1, new Coordinate(0, 0));
        battleShipManager.addShip(1, 1, new Coordinate(0, 3));
        // This test primarily checks for no exceptions and visual output, manual inspection would confirm correctness
        battleShipManager.viewBattleField(); 
    }
}
