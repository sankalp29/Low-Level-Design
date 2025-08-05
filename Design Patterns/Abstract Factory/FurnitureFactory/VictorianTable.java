package FurnitureFactory;

public class VictorianTable implements Table {

    @Override
    public void showTable() {
        System.out.println("Showing Victorian Table...");
    }

    @Override
    public void desc() {
        showTable();
    }
    
}
