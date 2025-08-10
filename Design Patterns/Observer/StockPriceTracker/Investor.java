public class Investor implements ISubscriber {
    private String name;

    public Investor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void receive(IPublisher publisher, int price) {
        System.out.println("Received update at " + name + ". Updated price for " + publisher.toString() + " : " + price);
    }
}
