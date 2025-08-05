package FurnitureFactory;

public class VictorianFurnitureStyle implements FurnitureStyle {

    @Override
    public Chair getChair() {
        return new VictorianChair();
    }

    @Override
    public Table getTable() {
        return new VictorianTable();
    }
}
