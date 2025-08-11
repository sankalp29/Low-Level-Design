package Observer.WeatherStationApp;

import java.util.Set;

public interface IPublisher {
    Set<ISubscriber> getSubscribers();
    String getName();
    int getTemperature();
    void setTemperature(int temperature);
    
    default void subscribe(ISubscriber subscriber) {
        getSubscribers().add(subscriber);
    }

    default void unsubscribe(ISubscriber subscriber) {
        getSubscribers().remove(subscriber);
    }

    default void changeTemperature(int newTemperature) {
        if (getTemperature() != newTemperature) {
            setTemperature(newTemperature);
            System.out.println(getName() + " temperature updated to " + newTemperature);
            notifySubscribers(newTemperature);
        }
    }

    default void notifySubscribers(int temperature) {
        if (getSubscribers().isEmpty()) {
            System.out.println("No subscribers for " + getName());
            return;
        }
        for (ISubscriber subscriber : getSubscribers()) {
            subscriber.receiveUpdate(this, temperature);
        }
    }
}
