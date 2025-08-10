public class Main {
    public static void main(String[] args) {
        ISubscriber sub1 = new Investor("Investor1");
        ISubscriber sub2 = new Investor("Investor2");
        ISubscriber sub3 = new Investor("Investor3");

        IPublisher reliance = new Stock("reliance", 100);
        IPublisher tcs = new Stock("tcs", 210);
        IPublisher laurus = new Stock("laurus", 85);

        reliance.subscribe(sub1);
        reliance.subscribe(sub2);
        reliance.subscribe(sub3);

        tcs.subscribe(sub1);

        laurus.subscribe(sub2);
        laurus.subscribe(sub3);

        reliance.publishPriceChange(110);
        laurus.publishPriceChange(90);
        tcs.publishPriceChange(200);

        laurus.unsubscribe(sub3);
        laurus.publishPriceChange(80);
    }
}