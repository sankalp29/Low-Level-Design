package srp_violated;

import java.util.List;

class Orders {
    
    private List<String> productIds;
    private String email;
    private String orderId;

    public Orders(List<String> productIds, String email, String orderId) {
        this.productIds = productIds;
        this.email = email;
        this.orderId = orderId;
    }

    // Responsibility 1
    public void placeOrder() {
        System.out.println("Order " + this.orderId + " placed for customer " + this.email);
        System.out.println("Products: " + String.join(", ", this.productIds));
    }

    // Responsibility 2
    public void sendNotification() {
        System.out.println("Notification sent to " + this.email + " for order " + this.orderId);
    }

    public static void main(String[] args) {
        Orders order = new Orders(List.of("laptop", "mouse"), "email@email.com", "ORD-001");
        order.placeOrder();
        order.sendNotification();
    }
}
