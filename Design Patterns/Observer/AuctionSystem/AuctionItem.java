package Observer.AuctionSystem;

import java.util.HashSet;
import java.util.Set;

public class AuctionItem implements IPublisher {

    private final Set<ISubscriber> subscribers;
    private String name;
    private final int minimumAskingPrice;
    private int highestBid;
    private ISubscriber highestBidBy;
    
    public AuctionItem(String name, int minimumAskingPrice) {
        this.subscribers = new HashSet<>();
        this.name = name;
        this.minimumAskingPrice = minimumAskingPrice;
        this.highestBid = 0;
    }

    public void placeBid(ISubscriber subscriber, int bid) {
        if (bid <= getHighestBid()) {
            System.out.println("Your bid is not higher than the current highest bid : Rs. " + getHighestBid() + "\n");
            return;
        }
        if (bid < minimumAskingPrice) {
            System.out.println("Please place a bid >= Rs. " + minimumAskingPrice);
            return;
        }
        setHighestBid(subscriber, bid);
        System.out.println("New highest bid for " + name + " at Rs. " + bid + " by " + subscriber.getName());
        notifySubscribers(bid);
    }

    void notifySubscribers(int bid) {
        if (getSubscribers().isEmpty()) {
            System.out.println("No subscribers for " + getName() + "\n");
            return;
        }
        for (ISubscriber subscriber : getSubscribers()) {
            if (subscriber.equals(highestBidBy)) continue;
            subscriber.receive(this, bid);
        }
        System.out.println();
    }

    public int getHighestBid() {
        return highestBid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<ISubscriber> getSubscribers() {
        return subscribers;
    }

    public void setHighestBid(ISubscriber subscriber, int bid) {
        this.highestBidBy = subscriber;
        this.highestBid = bid;
    }

    public int getMinimumAskingPrice() {
        return minimumAskingPrice;
    }

    public ISubscriber getHighestBidBy() {
        return highestBidBy;
    }
}
