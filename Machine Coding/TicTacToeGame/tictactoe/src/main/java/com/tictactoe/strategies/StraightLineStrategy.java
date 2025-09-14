package com.tictactoe.strategies;

import com.tictactoe.Symbol;
import com.tictactoe.TicTacToeBoard;

public class StraightLineStrategy implements WinningStrategy {

    @Override
    public boolean checkWinner(TicTacToeBoard ticTacToeBoard, Symbol symbol) {
        Symbol[][] board = ticTacToeBoard.getBoard();

        return checkRowWin(board, symbol) || checkColWin(board, symbol) || 
               checkTopLeftDiagonal(board, symbol) || checkTopRightDiagonal(board, symbol);
    
    }

    private boolean checkRowWin(Symbol[][] board, Symbol symbol) {
        for (int row = 0; row < board.length; row++) {
            boolean isWin = true;
            for (int col = 0; col < board.length; col++) {
                if (symbol != board[row][col]) {
                    isWin = false;
                    break;
                }
            }
            if (isWin) return true;
        }
        return false;
    }

    private boolean checkColWin(Symbol[][] board, Symbol symbol) {
        for (int col = 0; col < board.length; col++) {
            boolean isWin = true;
            for (int row = 0; row < board.length; row++) {
                if (symbol != board[row][col]) {
                    isWin = false;
                    break;
                }
            }
            if (isWin) return true;
        }
        return false;
    }

    private boolean checkTopLeftDiagonal(Symbol[][] board, Symbol symbol) {
        int row = 0, col = 0;
        while (row < board.length && col < board.length) {
            if (board[row][col] == null || board[row][col] != symbol) return false;
            row++;
            col++;
        }
        return true;
    }

    private boolean checkTopRightDiagonal(Symbol[][] board, Symbol symbol) {
        int row = 0, col = board.length - 1;
        
        while (row < board.length && col >= 0) {
            if (board[row][col] == null || board[row][col] != symbol) return false;
            row++;
            col--;
        }
        return true;
    }
}
