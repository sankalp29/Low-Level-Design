import java.util.List;

public interface Recordable {
    void record(double time);
    void recordInBatch(List<Double> timeInBatch);
}
