package NotificationFactory;

public class PushNotification implements Notification {

    @Override
    public void sendNotification(String recepient, String message) {
        System.out.println("Sending Push notification to " + recepient + " : " + message);
    }
}
