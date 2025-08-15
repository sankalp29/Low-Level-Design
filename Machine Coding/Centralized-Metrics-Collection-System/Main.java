public class Main {
    public static void main(String[] args) {
        MetricsClient ordersClient = new MetricsClient("orders-client");
        MetricsClient notifClient = new MetricsClient("notification-client");

        ordersClient.incrementCounter(MetricName.API_CALLS_TOTAL);
        ordersClient.incrementCounter(MetricName.API_CALLS_TOTAL);
        ordersClient.incrementCounter(MetricName.API_CALLS_TOTAL);

        System.out.println(ordersClient.getMetricsDetails());
    }
}
