import java.util.Set;

public interface IPublisher {
    Set<ISubscriber> getFollowers();
    String getName();

    default void addFollower(ISubscriber follower) {
        getFollowers().add(follower);
    }

    default void removeFollower(ISubscriber follower) {
        getFollowers().remove(follower);
    }

    default void post(String message) {
        System.out.println(getName() + " posted: \"" + message + "\"");
        for (ISubscriber follower : getFollowers()) {
            follower.receiveNotification(this, message);
        }
        System.out.println();
    }
}
