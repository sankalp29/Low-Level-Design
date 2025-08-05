package ThemeFactory;

public class LightOrderDetailsWidget implements OrderDetailsWidget{

    @Override
    public void showOrderDetails() {
        System.out.println("Light Order Details shown...");
    }

    @Override
    public void render() {
        showOrderDetails();   
    }
}
