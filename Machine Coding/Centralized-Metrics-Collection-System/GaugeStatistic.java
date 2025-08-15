public class GaugeStatistic implements Statistic, Settable {

    private volatile Double value = 0.0;
    private final String name;

    public GaugeStatistic(String name) {
        this.name = name;
    }

    @Override
    public Object getStats() {
        return value;    
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GaugeStatistic [value=" + value + ", name=" + name + "]";
    }
}
