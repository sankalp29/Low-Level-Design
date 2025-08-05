package FurnitureFactory;

public class ModernChair implements Chair {

    @Override
    public void showChair() {
        System.out.println("Showing Modern Chair...");
    }

    @Override
    public void desc() {
        showChair();
    }    
}
