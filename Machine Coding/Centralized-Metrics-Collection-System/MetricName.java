public enum MetricName {
    // Traffic metrics
    HTTP_REQUESTS_TOTAL(MetricType.COUNTER),
    API_CALLS_TOTAL(MetricType.COUNTER),

    // Latency metrics  
    RESPONSE_TIME_MS(MetricType.HISTOGRAM),
    DATABASE_QUERY_TIME_MS(MetricType.HISTOGRAM),

    // Error metrics
    HTTP_ERRORS_TOTAL(MetricType.COUNTER),
    DATABASE_ERRORS_TOTAL(MetricType.COUNTER),

    // Resource metrics
    MEMORY_USAGE_MB(MetricType.GAUGE),
    CPU_USAGE_PERCENT(MetricType.GAUGE),
    ACTIVE_CONNECTIONS(MetricType.GAUGE);

    private final MetricType type;

    MetricName(MetricType type) {
        this.type = type;
    }

    public MetricType getMetricType() { 
        return type;
    }
}