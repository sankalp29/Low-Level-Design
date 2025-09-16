package snakeandladder.display;

import snakeandladder.components.Player;

public class ConsoleDisplay implements IDisplay {
    @Override
    public void displayPlayerAddedNotification(Player player) {
        System.out.println("[Notification] : Added " + player.getName() + " to the game");
    }

    @Override
    public void displayStartGameNotification() {
        System.out.println("[Notification] : Starting game!");
    }

    @Override
    public void displayPlayerMove(Player player, int diceValue, int initialCellNo, int finalCellNo) {
        System.out.println("[Notification] : " + player.getName() + " rolled a " + diceValue + 
                           " and moved from " + initialCellNo + " to " + finalCellNo);
    }

    @Override
    public void displayWinNotification(Player player) {
        System.out.println("[Notification] : " + player.getName() + " has won the game!");
    }
}
