package Observer.WeatherStationApp;

public class Main {
    public static void main(String[] args) {
        IPublisher nashik = new Area("Nashik", 25);
        IPublisher mumbai = new Area("Mumbai", 28);
        IPublisher pune = new Area("Pune", 29);

        ISubscriber sankalp = new Person("Sankalp");
        ISubscriber janvi = new Person("Janvi");

        nashik.subscribe(sankalp);

        mumbai.subscribe(janvi);
        mumbai.subscribe(sankalp);

        nashik.changeTemperature(21);
        mumbai.changeTemperature(25);
        pune.changeTemperature(31);
    }
}
