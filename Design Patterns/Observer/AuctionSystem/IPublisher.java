package Observer.AuctionSystem;

import java.util.Set;

public interface IPublisher {
    Set<ISubscriber> getSubscribers();
    String getName();
    void placeBid(ISubscriber subscriber, int bid);

    default void subscribe(ISubscriber subscriber) {
        getSubscribers().add(subscriber);
    }

    default void unsubscribe(ISubscriber subscriber) {
        getSubscribers().remove(subscriber);
    }
}
