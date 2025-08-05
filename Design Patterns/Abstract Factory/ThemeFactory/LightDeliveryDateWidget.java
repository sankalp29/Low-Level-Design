package ThemeFactory;

public class LightDeliveryDateWidget implements DeliveryDateWidget {

    @Override
    public void showDeliveryDate() {
        System.out.println("Light Delivery Date shown...");
    }

    @Override
    public void render() {
        showDeliveryDate();   
    }
}
