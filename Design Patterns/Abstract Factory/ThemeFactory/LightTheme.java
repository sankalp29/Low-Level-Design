package ThemeFactory;

public class LightTheme implements Theme {

    @Override
    public DeliveryDateWidget generateDeliveryDateWidget() {
        return new LightDeliveryDateWidget();
    }

    @Override
    public OrderDetailsWidget generateOrderDetailsWidget() {
        return new LightOrderDetailsWidget();
    }
}
