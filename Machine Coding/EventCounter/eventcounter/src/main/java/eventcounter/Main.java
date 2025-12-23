package eventcounter;

public class Main {
    public static void main(String[] args) {
        EventCounter eventCounter = new EventCounter();

        eventCounter.recordEvent(10);
        eventCounter.recordEvent(20);
        eventCounter.recordEvent(20);
        eventCounter.recordEvent(30);

        System.out.println(eventCounter.getEventCount(10, 20));
        System.out.println(eventCounter.getEventCount(21, 30));
    }
}