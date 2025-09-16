package snakeandladder;

import java.util.LinkedList;
import java.util.Queue;

import snakeandladder.components.Board;
import snakeandladder.components.Player;
import snakeandladder.components.dice.IDice;
import snakeandladder.display.IDisplay;
import snakeandladder.exceptions.CellAlreadyOccupiedException;
import snakeandladder.exceptions.GameWithoutEnoughPlayersException;
import snakeandladder.exceptions.InvalidLadderConfigurationException;
import snakeandladder.exceptions.InvalidPlayerException;
import snakeandladder.exceptions.InvalidPositionException;
import snakeandladder.exceptions.InvalidSnakeConfigurationException;
import snakeandladder.exceptions.PlayerAlreadyExistsException;

public class SnakeAndLadderGame {
    private final Board board;
    private final IDice dice;
    private final Queue<Player> players;
    private final IDisplay gameDisplay;

    public SnakeAndLadderGame(final int size, IDice dice, IDisplay gameDisplay) {
        this.board = new Board(size);
        this.dice = dice;
        this.players = new LinkedList<>();
        this.gameDisplay = gameDisplay;
    }

    public void addPlayer(Player player) throws InvalidPlayerException, PlayerAlreadyExistsException {
        if (player == null) {
            throw new InvalidPlayerException("Cannot add a non-existent NULL player");
        }
        if (players.contains(player)) {
            throw new PlayerAlreadyExistsException("Player already added to game.");
        }

        players.add(player);
        gameDisplay.displayPlayerAddedNotification(player);
    }

    public void addSnake(final int snakeStart, final int snakeEnd) {
        try {
            board.addSnake(snakeStart, snakeEnd);
        } catch (InvalidSnakeConfigurationException | CellAlreadyOccupiedException | InvalidPositionException e) {
            e.printStackTrace();
        }
    }
    
    public void addLadder(final int ladderStart, final int ladderEnd) {
        try {
            board.addLadder(ladderStart, ladderEnd);
        } catch (InvalidLadderConfigurationException | CellAlreadyOccupiedException | InvalidPositionException e) {
            e.printStackTrace();
        }
    }

    public void startGame() throws GameWithoutEnoughPlayersException {
        if (players.size() < 2) {
            throw new GameWithoutEnoughPlayersException("Cannot start a game without players. Game should have atleast 2 players to start");
        }
        
        gameDisplay.displayStartGameNotification();

        while (true) { 
            Player currentPlayer = players.poll();
            int diceValue = dice.rollDice();
            int currentCellNo = currentPlayer.getCurrentCellNo();
            if (currentCellNo + diceValue > board.getLastCell()) {
                gameDisplay.displayPlayerMove(currentPlayer, diceValue, currentCellNo, currentCellNo);
            } else {
                int newCellNo = currentCellNo + diceValue;
                while (!board.isCellEmpty(newCellNo)) {
                    newCellNo = board.getCell(newCellNo).getCellComponent().getEnd();
                }
                gameDisplay.displayPlayerMove(currentPlayer, diceValue, currentCellNo, newCellNo);

                if (newCellNo == board.getLastCell()) {
                    gameDisplay.displayWinNotification(currentPlayer);
                    return;
                }

                currentPlayer.setCurrentCellNo(newCellNo);
            }

            players.add(currentPlayer);
        }
    }
}