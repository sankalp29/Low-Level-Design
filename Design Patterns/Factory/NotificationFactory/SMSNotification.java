package NotificationFactory;

public class SMSNotification implements Notification {

    @Override
    public void sendNotification(String recepient, String message) {
        System.out.println("Sending SMS Notification to " + recepient + " : " + message);
    }
}
