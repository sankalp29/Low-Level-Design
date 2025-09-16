package snakeandladder.display;

import snakeandladder.components.Player;

public interface IDisplay {
    
    void displayPlayerAddedNotification(Player player);
    void displayStartGameNotification();
    void displayPlayerMove(Player player, int diceValue, int initialCellNo, int finalCellNo);
    void displayWinNotification(Player player);
}
