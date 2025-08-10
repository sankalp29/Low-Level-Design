import java.util.Set;

public interface IPublisher {
    Set<ISubscriber> getSubscribers();

    default void subscribe(ISubscriber subscriber) {
        getSubscribers().add(subscriber);
    }

    default void unsubscribe(ISubscriber subscriber) {
        getSubscribers().remove(subscriber);
    }

    default void publish(String message) {
        System.out.println("\n[" + this + "] Publishing: " + message);
        System.out.println("Notifying " + getSubscribers().size() + " subscriber(s)...");
        notifySubscribers(message);
    }

    default void notifySubscribers(String message) {
        for (ISubscriber subscriber : getSubscribers()) {
            subscriber.receive(this, message);
        }
    }
}