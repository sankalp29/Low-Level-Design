package com.tictactoe;

import lombok.Getter;

@Getter
public class TicTacToeBoard {
    private int boardSize;
    private final Symbol[][] board;

    public TicTacToeBoard(int size) {
        this.boardSize = size;
        board = new Symbol[size][size];
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == null;
    }

    public void setCell(int row, int col, Symbol symbol) {
        board[row][col] = symbol;
    }
}
