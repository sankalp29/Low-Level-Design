public interface ISubscriber {
    void receive(IPublisher publisher, int price);
}
