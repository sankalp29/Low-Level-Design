import java.util.HashMap;
import java.util.Map;

public class MetricsCollector {
    private final Map<String, Statistic> metricStatistics;
    private static MetricsCollector INSTANCE;

    public static MetricsCollector getInstance() {
        if (INSTANCE == null) {
            synchronized (MetricsCollector.class) {
                if (INSTANCE == null) INSTANCE = new MetricsCollector();
            }
        }
        return INSTANCE;
    }

    private MetricsCollector() {
        metricStatistics = new HashMap<>();
    }

    public Statistic getMetric(String serviceName, MetricName metricName) {
        String key = createKey(serviceName, metricName);
        return metricStatistics.computeIfAbsent(key, stat -> createStatistic(metricName));
    }

    private Statistic createStatistic(MetricName metricName) {
        switch (metricName.getMetricType()) {
            case COUNTER : return new CounterStatistic(metricName.name());
            case HISTOGRAM : return new HistogramStatistic(metricName.name());
            case GAUGE : return new GaugeStatistic(metricName.name());
            default : throw new AssertionError();
        }
    }

    private String createKey(String serviceName, MetricName metricType) {
        return (serviceName + " : " + metricType.toString());
    }

    public Map<String, Statistic> getMetricStatistics() {
        return Map.copyOf(metricStatistics);
    }
}
