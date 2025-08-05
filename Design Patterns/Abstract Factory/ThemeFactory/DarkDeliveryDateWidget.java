package ThemeFactory;

public class DarkDeliveryDateWidget implements DeliveryDateWidget {

    @Override
    public void showDeliveryDate() {
        System.out.println("Dark Delivery Date shown...");
    }

    @Override
    public void render() {
        showDeliveryDate();   
    }
}
