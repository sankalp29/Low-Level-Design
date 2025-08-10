import java.util.HashSet;
import java.util.Set;

public class User implements ISubscriber, IPublisher {
    private String name;
    private final Set<ISubscriber> followers;

    public User(String name) {
        this.name = name;
        this.followers = new HashSet<>();
    }

    @Override
    public void receiveNotification(IPublisher publisher, String message) {
        System.out.println(getName() + " saw " + publisher.getName() + "'s post: \"" + message + "\"");
    }

    @Override
    public Set<ISubscriber> getFollowers() {
        return followers;
    }

    public String getName() {
        return name;
    }
}