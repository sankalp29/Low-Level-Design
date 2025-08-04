package NotificationFactory;

public class Main {
    public static void main(String[] args) {
        Notification notification = NotificationFactory.getNotification("sms");
        notification.sendNotification("recepient1", "message1");
    }
}