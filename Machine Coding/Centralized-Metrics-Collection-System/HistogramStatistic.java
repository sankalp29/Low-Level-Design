import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HistogramStatistic implements Statistic, Recordable {
    private final String name;
    private final List<Double> values = new CopyOnWriteArrayList<>();

    public HistogramStatistic(String name) {
        this.name = name;
    }

    @Override
    public Object getStats() {
        return new ArrayList<>(values);
    }

    @Override
    public void record(double time) {
        values.add(time);
    }

    @Override
    public void recordInBatch(List<Double> timeInBatch) {
        values.addAll(timeInBatch);
    }

    @Override
    public String toString() {
        return "HistogramStatistic [name=" + name + ", values=" + values + "]";
    }
}
