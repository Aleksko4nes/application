package application.processor.sorting;

import java.util.Comparator;
import java.util.List;

public class Sorter<T> {
    private SortingStrategy<T> strategy;

    public Sorter(SortingStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public List<T> sort(List<T> collection, Comparator<? super T> comparator) {
        return this.strategy.sort(collection, comparator);
    }
}
