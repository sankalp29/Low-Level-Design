package srp_adhered;

import java.util.List;

class Orders {
    private List<String> productIds;
    private String email;
    private String orderId;

    public List<String> getProductIds() {
        return productIds;
    }

    public String getEmail() {
        return email;
    }

    public String getOrderId() {
        return orderId;
    }

    public Orders(List<String> productIds, String email, String orderId) {
        this.productIds = productIds;
        this.email = email;
        this.orderId = orderId;
    }

    public void placeOrder() {
        System.out.println("Order " + this.orderId + " placed for customer " + this.email);
        System.out.println("Products: " + String.join(", ", this.productIds));
    }
}
