package OCP_Violated;
import java.util.List;

class Notification {
    String email, message;

    public Notification(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public void sendSMSNotification() {
        System.out.println("Sent SMS notification to " + email + " : " + message);
    }


    /**
     * A new requirement comes in to send Email notifications.
     * We add the sendEmailNotification() functionality in the Notification class itself.
    */
    public void sendEmailNotification() {
        System.out.println("Sent Email notification to " + email + " : " + message);
    }
    
}

class Orders {
    private List<String> products;
    private String email;
    
    public List<String> getProducts() {
        return products;
    }

    public String getEmail() {
        return email;
    }

    public Orders(List<String> products, String email) {
        this.products = products;
        this.email = email;
    }

    public void placeOrder() {
        System.out.println("Order placed for " + email + " : Products = " + products.toString());
    }
}

public class Main {
    public static void main(String[] args) {
        Orders order = new Orders(List.of("Laptop", "Mouse"), "email@email.com");
        Notification notification = new Notification(order.getEmail(), "Order sent notification");

        notification.sendEmailNotification();
    }
}