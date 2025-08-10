public interface ISubscriber {
    void receiveNotification(IPublisher publisher, String message);
}