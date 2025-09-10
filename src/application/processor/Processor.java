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
    public T findElementInCollectionByBinarySearch(List<T> collection, String name) {
        return searchStrategy.search(collection, name);
    }

    @Override
    public List<T> sortCollection(List<T> collection) {
        return sortingStrategy.sort(collection);
    }
}
