package eventcounter;

public class Event {
    private final Integer timestamp;
    private int noOfEvents;
    private int prefixEventSum;

    public void incrementEventsCount() {
        this.noOfEvents++;
        this.prefixEventSum++;
    }

    public Event(int timestamp, int prefixEventSum) {
        this.timestamp = timestamp;
        this.noOfEvents = 1;
        this.prefixEventSum = prefixEventSum;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getNoOfEvents() {
        return noOfEvents;
    }

    public int getPrefixEventSum() {
        return prefixEventSum;
    }

    @Override
    public String toString() {
        return "Event [timestamp=" + timestamp + ", noOfEvents=" + noOfEvents + "]";
    }
}