package snakeandladder.exceptions;

public class GameWithoutEnoughPlayersException extends Exception {
    public GameWithoutEnoughPlayersException(String message) {
        super(message);
    }
}
