
import java.util.concurrent.atomic.AtomicInteger;

public class CounterStatistic implements Statistic, Incrementable {
    private final String name;

    public CounterStatistic(String name) {
        this.name = name;    
    }

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Integer getStats() {
        return counter.get();
    }

    @Override
    public void batchIncrement(int incrementValue) {
        counter.addAndGet(incrementValue);
    }

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public String toString() {
        return "CounterStatistic [name=" + name + ", counter=" + counter + "]";
    }
}
