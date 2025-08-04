package NotificationFactory;

public class EmailNotification implements Notification {

    @Override
    public void sendNotification(String recepient, String message) {
        System.out.println("Sending Email notification to " + recepient + " : " + message);
    }
}
