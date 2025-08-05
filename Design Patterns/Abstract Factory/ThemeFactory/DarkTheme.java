package ThemeFactory;

public class DarkTheme implements Theme {

    @Override
    public DeliveryDateWidget generateDeliveryDateWidget() {
        return new DarkDeliveryDateWidget();
    }

    @Override
    public OrderDetailsWidget generateOrderDetailsWidget() {
        return new DarkOrderDetailsWidget();
    }
    
}
