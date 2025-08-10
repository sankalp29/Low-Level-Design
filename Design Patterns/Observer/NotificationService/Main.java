public class Main {
    public static void main(String[] args) {
        // Subscribers
        ISubscriber email1 = new EmailSubscriber("email1@example.com");
        ISubscriber sms1 = new SMSSubscriber("+91 1111111111");
        ISubscriber email2 = new EmailSubscriber("email2@example.com");
        ISubscriber push1 = new PushSubscriber("device-xyz");

        // Publishers (Channels)
        Channel channel1 = new Channel("Neetcode");
        Channel channel2 = new Channel("Think School");

        // Subscriptions
        channel1.subscribe(email1);
        channel1.subscribe(sms1);
        channel1.subscribe(email2);
        channel1.subscribe(push1);

        channel2.subscribe(email1);
        channel2.subscribe(sms1);
        channel2.subscribe(push1);

        // Publish messages
        channel1.publish("New coding tutorial on Arrays");
        channel2.publish("New case study on Apple");

        // Unsubscribe and publish again
        channel1.unsubscribe(email1);
        channel1.publish("New coding tutorial on Dynamic Programming");
    }
}
