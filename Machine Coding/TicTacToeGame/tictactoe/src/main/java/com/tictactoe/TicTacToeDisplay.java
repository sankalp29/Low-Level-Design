package com.tictactoe;

public class TicTacToeDisplay {
    public void displayBoard(Symbol[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + (board[i][j] == null ? " " : board[i][j]));
                if (j < board[i].length - 1) System.out.print(" |");
            }
            System.out.println();
            if (i < board.length - 1) {
                printSeparatorLine(board.length);
            }
        }
    }

    public void displayBoardCellNumbers(Symbol[][] board) {
        System.out.println("Refer to the board cell numbers for reference:\n");
        int cellNumber = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + (cellNumber < 10 ? " " : "") + cellNumber);
                if (j < board[i].length - 1) System.out.print(" |");
                cellNumber++;
            }
            System.out.println();
            if (i < board.length - 1) {
                printSeparatorLine(board.length);
            }
        }

        System.out.println();
        System.out.println();
    }

    public void printPlayerTurnMessage(int chanceId, Player player1, Player player2) {
        System.out.println(((chanceId % 2 == 0) ? player1.getName() : player2.getName()) + "'s turn");
        System.out.println("Choose a cell number");
    }

    public void printPlayerWinMessage(Player winner) {
        System.out.println(winner.getName() + " has won the game");
    }

    private void printSeparatorLine(int boardSize) {
        int lineLength = boardSize * 4;
        for (int k = 0; k < lineLength; k++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
