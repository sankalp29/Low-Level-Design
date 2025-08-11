package Observer.WeatherStationApp;

public interface ISubscriber {
    String getName();
    
    default void receiveUpdate(IPublisher publisher, int temperature) {
        System.out.println("[Notification] " + getName() + 
            ": " + publisher.getName() + " temperature is now " + temperature + "°C");
    }
    
}
