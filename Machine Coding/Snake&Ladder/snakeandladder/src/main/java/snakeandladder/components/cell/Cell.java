package snakeandladder.components.cell;

import lombok.Getter;

@Getter
public class Cell {
    private final int cellNo;
    private CellEntity cellComponent;

    public Cell(final int cellNo) {
        this.cellNo = cellNo;
        this.cellComponent = null;
    }

    public void addComponent(CellEntity component) {
        this.cellComponent = component;
    }

    public void removeComponent() {
        this.cellComponent = null;
    }

    public boolean isEmpty() {
        return cellComponent == null;
    }
}
