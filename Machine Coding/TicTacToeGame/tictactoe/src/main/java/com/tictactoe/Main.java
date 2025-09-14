package com.tictactoe;

import com.tictactoe.exceptions.SameSymbolsChosenException;
import com.tictactoe.strategies.StraightLineStrategy;

public class Main {
    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame(4, new StraightLineStrategy());
        try {
            game.startGame(new Player("Sankalp", Symbol.X), new Player("Janvi", Symbol.O));
        } catch (SameSymbolsChosenException e) {
            e.printStackTrace();
        }
    }
}