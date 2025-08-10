public class SMSSubscriber implements ISubscriber {
    private final String phoneNumber;

    public SMSSubscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void receive(IPublisher publisher, String message) {
        System.out.println("📱 Sending SMS to " + phoneNumber);
        display(publisher, message);
    }
}
