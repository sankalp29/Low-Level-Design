package FurnitureFactory;

public class ModernTable implements Table {

    @Override
    public void showTable() {
        System.out.println("Showing Modern Table...");
    }

    @Override
    public void desc() {
        showTable();
    }
}
