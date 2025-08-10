public class PushSubscriber implements ISubscriber {
    private final String deviceId;

    public PushSubscriber(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void receive(IPublisher publisher, String message) {
        System.out.println("🔔 Sending Push Notification to device " + deviceId);
        display(publisher, message);
    }
}
