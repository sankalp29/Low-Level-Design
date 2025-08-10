import java.util.HashSet;
import java.util.Set;

public class Stock implements IPublisher {

    private String name;
    private Integer price;
    private final Set<ISubscriber> subscribers;

    public Stock(String name, Integer price) {
        this.subscribers = new HashSet<>();
        this.name = name;
        this.price = price;
    }

    @Override
    public Integer getCurrentPrice() {
        return price;
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
    public void setPrice(int price) {
        this.price = price;
    }
}
