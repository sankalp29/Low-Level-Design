package srp_adhered;

import java.util.List;

public class OrderProcessor {
    private final Orders order;
    private final Notification notification;

    public OrderProcessor(Orders order, Notification notification) {
        this.order = order;
        this.notification = notification;
    }

    public void processOrder() {
        order.placeOrder();
        String message = "Your order " + this.order.getOrderId() + " has been successfully placed.";
        notification.sendNotification(order.getEmail(), message);
    }

    public static void main(String[] args) {
        Orders order = new Orders(List.of("laptop", "mouse"), "email@email.com", "ORD-001");
        Notification notification = new Notification();
        OrderProcessor orderProcessor = new OrderProcessor(order, notification);

        orderProcessor.processOrder();
    }
}
