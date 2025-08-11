package Observer.AuctionSystem;

public interface ISubscriber {
    String getName();

    default void receive(IPublisher publisher, int bid) {
        System.out.println("[Notification] " + getName() + " : Highest bid for " + publisher.getName() + " placed at Rs. " + bid);
    }
}
