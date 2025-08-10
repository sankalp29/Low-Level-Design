import java.util.Set;

public interface IPublisher {
    Set<ISubscriber> getSubscribers();
    Integer getCurrentPrice();
    void setPrice(int price);
    String getName();

    default void subscribe(ISubscriber subscriber) {
        getSubscribers().add(subscriber);
    }

    default void unsubscribe(ISubscriber subscriber) {
        getSubscribers().remove(subscriber);
    }

    default void publishPriceChange(int newPrice) {
        if (getCurrentPrice() != newPrice) {
            setPrice(newPrice);
            notifySubscribers(newPrice);
        }
    }

    default void notifySubscribers(int newPrice) {
        System.out.println("Notifying all subscribers of price change in Stock " + getName());
        for (ISubscriber subscriber : getSubscribers()) {
            subscriber.receive(this, newPrice);
        }
        System.out.println();
    }
}
