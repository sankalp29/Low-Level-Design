package ThemeFactory;

public class DarkOrderDetailsWidget implements OrderDetailsWidget {

    @Override
    public void showOrderDetails() {
        System.out.println("Dark Order Details shown...");
    }

    @Override
    public void render() {
        showOrderDetails();
    }
}
