package application.processor;

import application.entity.Person;
import application.processor.input.InputStrategy;
import application.processor.searching.SearchStrategy;
import application.processor.sorting.SortingStrategy;

import java.util.List;

public class Processor<T extends Person> implements ProcessCollection<T>{
    private  SearchStrategy<T> searchStrategy;
    private  SortingStrategy<T> sortingStrategy;
    private  InputStrategy<T> inputStrategy;

    public void setSearchStrategy(SearchStrategy<T> searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public SearchStrategy<T> getSearchStrategy() {
        return searchStrategy;
    }

    public void setSortingStrategy(SortingStrategy<T> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void setInputStrategy(InputStrategy<T> inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    @Override
    public List<T> fillCollection(String s) {
        return inputStrategy.load(s);
    }

    @Override
    public T findElementInCollectionByBinarySearch(List<T> collection, String key) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return searchStrategy.search(collection, key);
    }

    @Override
    public List<T> sortCollection(List<T> collection) {
        return sortingStrategy.sort(collection);
    }
}
