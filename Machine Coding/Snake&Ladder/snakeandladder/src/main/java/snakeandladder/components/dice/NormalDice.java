package snakeandladder.components.dice;

import java.util.Random;

public class NormalDice implements IDice {

    @Override
    public int rollDice() {
        Random random = new Random();
        return random.nextInt(1, 7);
    }
}
