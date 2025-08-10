public interface ISubscriber {
    void receive(IPublisher publisher, String message);

    default void display(IPublisher publisher, String message) {
        System.out.println("New message from " + publisher + " received: " + message);
    }
}