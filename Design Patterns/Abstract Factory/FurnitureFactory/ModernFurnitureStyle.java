package FurnitureFactory;

public class ModernFurnitureStyle implements FurnitureStyle {

    @Override
    public Chair getChair() {
        return new ModernChair();
    }

    @Override
    public Table getTable() {
        return new ModernTable();
    }
    
}
