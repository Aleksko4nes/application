package application.manager.sorting;

import java.util.List;

public class Sorter<T> {
    private SortingStrategy<T> strategy;

    public Sorter(SortingStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public List<T> sort(List<T> collection){
        return strategy.sort(collection);
    }
}
