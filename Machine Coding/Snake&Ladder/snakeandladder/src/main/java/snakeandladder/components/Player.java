package snakeandladder.components;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class Player {
    private final String name;
    @Setter
    private int currentCellNo;

    public Player(String name) {
        this.name = name;
        this.currentCellNo = 0;
    }
}