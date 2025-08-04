package NotificationFactory;

public class NotificationFactory {
    public static Notification getNotification(String type) throws IllegalArgumentException {
        if (type.equalsIgnoreCase("sms")) return new SMSNotification();
        if (type.equalsIgnoreCase("email")) return new EmailNotification();
        if (type.equalsIgnoreCase("push")) return new PushNotification();

        throw new IllegalArgumentException();
    }
}
