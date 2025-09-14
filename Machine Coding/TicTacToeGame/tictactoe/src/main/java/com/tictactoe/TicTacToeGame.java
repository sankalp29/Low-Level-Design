package com.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.tictactoe.exceptions.CellOccupiedException;
import com.tictactoe.exceptions.InvalidCellException;
import com.tictactoe.exceptions.SameSymbolsChosenException;
import com.tictactoe.strategies.WinningStrategy;

import lombok.Setter;

public class TicTacToeGame {
    private final TicTacToeBoard board;
    @Setter
    private WinningStrategy winningStrategy;
    private final TicTacToeDisplay display;
    private Player currentPlayerTurn;

    public TicTacToeGame(int n, WinningStrategy winningStrategy) {
        board = new TicTacToeBoard(n);
        this.winningStrategy = winningStrategy;
        this.display = new TicTacToeDisplay();
    }

    public void startGame(Player player1, Player player2) throws SameSymbolsChosenException{
        if (player1.getSymbol() == player2.getSymbol()) 
            throw new SameSymbolsChosenException("Players cannot choose the same symbol");
        
        int chances = 0, boardSize = board.getBoardSize();
        currentPlayerTurn = player1;

        display.displayBoardCellNumbers(board.getBoard());
        display.displayBoard(board.getBoard());

        try (Scanner in = new Scanner(System.in)) {
            
            while (chances < boardSize * boardSize) {                
                
                display.printPlayerTurnMessage(chances, player1, player2);

                try {
                    Integer cell = in.nextInt();
                    if (cell < 1 || cell > boardSize * boardSize) 
                        throw new InvalidCellException("Choose a valid cell number between 1 & " + (boardSize * boardSize) + " inclusive");
                    cell = cell - 1;
                    int row = cell / boardSize, col = cell % boardSize;
                    
                    if (!board.isCellEmpty(row, col)) 
                        throw new CellOccupiedException("You have chosen an already occupied cell. Choose a different cell");
                    
                    board.setCell(row, col, (chances % 2 == 0) ? player1.getSymbol() : player2.getSymbol());
                    
                    display.displayBoard(board.getBoard());

                    if (winningStrategy.checkWinner(board, currentPlayerTurn.getSymbol())) {
                        display.printPlayerWinMessage(currentPlayerTurn);
                        return;
                    }

                } catch (InputMismatchException | InvalidCellException | CellOccupiedException e) {
                    System.out.println(e.getMessage());
                    in.nextLine();
                    continue;
                }

                currentPlayerTurn = switchTurn(player1, player2);
                chances++;
            }
        }

        System.out.println("The game has ended in a draw");
    }

    private Player switchTurn(Player player1, Player player2) {
        return (currentPlayerTurn == player1) ? player2 : player1;
    }
}
