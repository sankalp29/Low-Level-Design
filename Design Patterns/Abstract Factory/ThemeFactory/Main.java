package ThemeFactory;

public class Main {
    public static void main(String[] args) {
        Theme theme = ThemeFactory.getTheme("light");
        DeliveryDateWidget deliveryDateWidget = theme.generateDeliveryDateWidget();
        OrderDetailsWidget orderDetailsWidget = theme.generateOrderDetailsWidget();

        deliveryDateWidget.render();
        orderDetailsWidget.render();
    }
}
