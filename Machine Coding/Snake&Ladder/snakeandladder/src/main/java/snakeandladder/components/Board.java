package snakeandladder.components;

import snakeandladder.components.cell.Cell;
import snakeandladder.components.cell.CellEntity;
import snakeandladder.components.cell.Ladder;
import snakeandladder.components.cell.Snake;
import snakeandladder.exceptions.CellAlreadyOccupiedException;
import snakeandladder.exceptions.InvalidLadderConfigurationException;
import snakeandladder.exceptions.InvalidPositionException;
import snakeandladder.exceptions.InvalidSnakeConfigurationException;

public class Board {
    private final Cell[] cells;
    private final int size;
    
    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size];
        for (int i = 0; i < size; i++) cells[i] = new Cell(i);
    }

    public void addSnake(final int snakeStart, final int snakeEnd) throws InvalidSnakeConfigurationException, CellAlreadyOccupiedException, InvalidPositionException {
        if (!isValid(snakeStart, snakeEnd)) {
            throw new InvalidPositionException("Snake coordinates go out of bounds. Cannot add Snake.");
        }
        if (snakeStart <= snakeEnd) {
            throw new InvalidSnakeConfigurationException("Snake's mouth must be > Snake's tail");
        }
        if (!cells[snakeStart].isEmpty()) {
            throw new CellAlreadyOccupiedException("Cannot add Snake at " + snakeStart + ". The cell is already occupied.");
        }
        
        CellEntity snake = new Snake(snakeStart, snakeEnd);
        cells[snakeStart].addComponent(snake);
    }

    public void addLadder(final int ladderStart, final int ladderEnd) throws InvalidLadderConfigurationException, CellAlreadyOccupiedException, InvalidPositionException {
        if (!isValid(ladderStart, ladderEnd)) {
            throw new InvalidPositionException("Ladder coordinates go out of bounds. Cannot add Ladder.");
        }
        if (ladderStart >= ladderEnd) {
            throw new InvalidLadderConfigurationException("Ladder's start must be < Ladder' end");
        }
        if (!cells[ladderStart].isEmpty()) {
            throw new CellAlreadyOccupiedException("Cannot add Ladder at " + ladderStart + ". The cell is already occupied.");
        }
        
        CellEntity ladder = new Ladder(ladderStart, ladderEnd);
        cells[ladderStart].addComponent(ladder);
    }

    public boolean isCellEmpty(final int cellNo) {
        return cells[cellNo].isEmpty();
    }

    public Cell getCell(final int cellNo) {
        return cells[cellNo];
    }

    public int getLastCell() {
        return size - 1;
    }

    private boolean isValid(final int start, final int end) {
        return start >= 0 && start < size && end >= 0 && end < size;
    }
}