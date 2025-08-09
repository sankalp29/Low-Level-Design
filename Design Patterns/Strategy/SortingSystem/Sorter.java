package SortingSystem;

public class Sorter<T extends Comparable<T>> {
    private SortingStrategy<T> sortingStrategy;

    public Sorter(SortingStrategy<T> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }
    
    public void setSortingStrategy(SortingStrategy<T> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public T[] sort(T[] data) {
        return sortingStrategy.sort(data);
    }
}
