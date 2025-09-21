package application.processor.searching;

import java.util.List;

public class Searcher<T> {

    private SearchStrategy<T> strategy;

    public Searcher(SearchStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SearchStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public List<T> search(List<T> collection, String key) {
        return strategy.search(collection, key);
    }
}
