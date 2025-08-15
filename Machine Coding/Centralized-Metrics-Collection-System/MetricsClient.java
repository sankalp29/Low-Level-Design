import java.util.List;
import java.util.Map;

public class MetricsClient {
    private final String serviceName;
    private final MetricsCollector metricsCollector;

    public MetricsClient(String serviceName) {
        this.serviceName = serviceName;
        this.metricsCollector = MetricsCollector.getInstance();
    }

    public void incrementCounter(MetricName metricName) {
        if (metricName.getMetricType() != MetricType.COUNTER) 
            throw new IllegalArgumentException("Illegal metric name passed. " + metricName + " is not a counter");
        
        Statistic statistic = metricsCollector.getMetric(serviceName, metricName);
        ((Incrementable) statistic).increment();
    }

    public void incrementCounterInBatch(MetricName metricName, int incrementBatch) {
        if (metricName.getMetricType() != MetricType.COUNTER) 
            throw new IllegalArgumentException("Illegal metric name passed. " + metricName + " is not a counter");
        
        Statistic statistic = metricsCollector.getMetric(serviceName, metricName);
        ((Incrementable) statistic).batchIncrement(incrementBatch);
    }

    public void record(MetricName metricName, double value) {
        if (metricName.getMetricType() != MetricType.HISTOGRAM) 
            throw new IllegalArgumentException("Illegal metric name passed. " + metricName + " is not a histogram");
        
        Statistic statistic = metricsCollector.getMetric(serviceName, metricName);
        ((Recordable) statistic).record(value);
    }

    public void recordInBatch(MetricName metricName, List<Double> value) {
        if (metricName.getMetricType() != MetricType.HISTOGRAM) 
            throw new IllegalArgumentException("Illegal metric name passed. " + metricName + " is not a histogram");
        
        Statistic statistic = metricsCollector.getMetric(serviceName, metricName);
        ((Recordable) statistic).recordInBatch(value);
    }

    public void setValue(MetricName metricName, Double value) {
        if (metricName.getMetricType() != MetricType.GAUGE) 
            throw new IllegalArgumentException("Illegal metric name passed. " + metricName + " is not a gauge");
        
        Statistic statistic = metricsCollector.getMetric(serviceName, metricName);
        ((Settable) statistic).setValue(value);
    }

    public Map<String, Statistic> getMetricsDetails() {
        return metricsCollector.getMetricStatistics();
    }
}
