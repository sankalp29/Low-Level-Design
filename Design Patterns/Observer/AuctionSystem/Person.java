package Observer.AuctionSystem;

public class Person implements ISubscriber {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
