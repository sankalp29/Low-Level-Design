import java.util.HashSet;
import java.util.Set;

public class Channel implements IPublisher {
    private final String name;
    private final Set<ISubscriber> subscribers = new HashSet<>();

    public Channel(String name) {
        this.name = name;
    }

    @Override
    public Set<ISubscriber> getSubscribers() {
        return subscribers;
    }

    @Override
    public String toString() {
        return name;
    }
}
