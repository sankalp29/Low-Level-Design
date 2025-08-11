package Observer.WeatherStationApp;

import java.util.HashSet;
import java.util.Set;

public class Area implements IPublisher {

    private String name;
    private Set<ISubscriber> subscribers;
    private int temperature;

    public Area(String name, int temperature) {
        this.name = name;
        this.temperature = temperature;
        this.subscribers = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<ISubscriber> getSubscribers() {
        return subscribers;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    
}
