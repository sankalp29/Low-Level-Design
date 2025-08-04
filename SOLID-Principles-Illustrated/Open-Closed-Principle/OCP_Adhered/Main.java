package OCP_Adhered;

import java.util.List;

/**
 * I have assumed that all Notification types require the same attributes (email, message).
 * If each notification type requires different attributes, then we can set Notification as an interface.
 */
abstract class Notification {
    private String email;
    private String message;

    public abstract void sendNotification();

    public Notification(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}

class SMSNotification extends Notification {

    public SMSNotification(String email, String message) {
        super(email, message);
    }

    @Override
    public void sendNotification() {
        System.out.println("Sent SMS notification to " + this.getEmail() + " : " + this.getEmail());
    }
}

/**
 * New requirement came in to send EmailNotification to buyers.
 * No need to make changes to exisiting Notification class.
 */
class EmailNotification extends Notification {

    public EmailNotification(String email, String message) {
        super(email, message);
    }

    @Override
    public void sendNotification() {
        System.out.println("Sent Email notification to " + this.getEmail() + " : " + this.getEmail());
    }
}

class Orders {
    private List<String> products;
    private String email;

    public Orders(List<String> products, String email) {
        this.products = products;
        this.email = email;
    }

    public List<String> getProducts() {
        return products;
    }

    public String getEmail() {
        return email;
    }

    public void placeOrder() {
        System.out.println("Order placed for " + email + " : Products = " + products.toString());
    }
}

public class Main {
    public static void main(String[] args) {
        Orders order = new Orders(List.of("Laptop", "Mouse"), "email@email.com");
        String message = "Your order has been placed. Products ordered : " + order.getProducts().toString();
        Notification notification = new SMSNotification(order.getEmail(), message);
        notification.sendNotification();
        notification = new EmailNotification(order.getEmail(), message);
        notification.sendNotification();
    }
}
