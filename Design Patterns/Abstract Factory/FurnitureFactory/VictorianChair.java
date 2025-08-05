package FurnitureFactory;

public class VictorianChair implements Chair {

    @Override
    public void showChair() {
        System.out.println("Showing Victorian Chair...");
    }

    @Override
    public void desc() {
        showChair();
    }   
}
