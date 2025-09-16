package snakeandladder;

import snakeandladder.components.Player;
import snakeandladder.components.dice.NormalDice;
import snakeandladder.display.ConsoleDisplay;
import snakeandladder.exceptions.GameWithoutEnoughPlayersException;
import snakeandladder.exceptions.InvalidPlayerException;
import snakeandladder.exceptions.PlayerAlreadyExistsException;

public class Main {
    public static void main(String[] args) {
        // testSameCellAddition();
        // testWithZeroPlayers();
        testHappyCase();
    }

    private static void testHappyCase() {
        SnakeAndLadderGame game = new SnakeAndLadderGame(100, new NormalDice(), new ConsoleDisplay());
        game.addLadder(1, 10);
        game.addLadder(80, 98);
        game.addLadder(35, 47);

        game.addSnake(7, 2);
        game.addSnake(57, 6);
        game.addSnake(89, 22);
        game.addSnake(98, 4);

        try {
            game.addPlayer(new Player("Sankalp"));
            game.addPlayer(new Player("Janvi"));
            game.addPlayer(new Player("Khushbu"));
            game.startGame();
        } catch (InvalidPlayerException | PlayerAlreadyExistsException | GameWithoutEnoughPlayersException e) {
            e.printStackTrace();
        }

        
    }

    private static void testWithZeroPlayers() {
        SnakeAndLadderGame game = new SnakeAndLadderGame(100, new NormalDice(), new ConsoleDisplay());
        game.addLadder(1, 10);
        game.addLadder(15, 27);
        game.addSnake(16, 6);

        try {
            game.startGame();
        } catch (GameWithoutEnoughPlayersException e) {
            e.printStackTrace();
        }
    }

    private static void testSameCellAddition() {
        SnakeAndLadderGame game = new SnakeAndLadderGame(100, new NormalDice(), new ConsoleDisplay());
        game.addLadder(1, 10);
        game.addLadder(15, 27);
        game.addSnake(15, 6);
    }
}