package Observer.WeatherStationApp;

public class Person implements ISubscriber {
    private final String name;
    
    public Person(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
