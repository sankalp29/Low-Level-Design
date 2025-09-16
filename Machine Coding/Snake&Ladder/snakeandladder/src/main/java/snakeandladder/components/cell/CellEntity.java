package snakeandladder.components.cell;

import lombok.Getter;

@Getter
public abstract class CellEntity {
    private final int start;
    private final int end;

    public CellEntity(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
