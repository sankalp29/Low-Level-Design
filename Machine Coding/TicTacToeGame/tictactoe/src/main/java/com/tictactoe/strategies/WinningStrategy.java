package com.tictactoe.strategies;

import com.tictactoe.Symbol;
import com.tictactoe.TicTacToeBoard;

public interface WinningStrategy {
    public boolean checkWinner(TicTacToeBoard board, Symbol symbol);
}
