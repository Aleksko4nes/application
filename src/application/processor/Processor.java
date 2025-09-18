package application.processor;

import application.entity.Person;
import application.processor.input.Importer;
import application.processor.input.InputStrategy;
import application.processor.searching.SearchStrategy;
import application.processor.searching.Searcher;
import application.processor.sorting.Sorter;
import application.processor.sorting.SortingStrategy;

import java.util.Comparator;
import java.util.List;

public class Processor<T extends Person> implements ProcessCollection<T>{
    private Searcher<T> searcher;
    private Sorter<T> sorter;
    private Importer<T> importer;

    public Processor(Searcher<T> searcher, Sorter<T> sorter, Importer<T> importer) {
        this.searcher = searcher;
        this.sorter = sorter;
        this.importer = importer;
    }

    public Processor() {
    }

    public void setSearcher(Searcher<T> searcher) {
        this.searcher = searcher;
    }

    public void setSorter(Sorter<T> sorter) {
        this.sorter = sorter;
    }

    public void setImporter(Importer<T> importer) {
        this.importer = importer;
    }

    @Override
    public List<T> fillCollection(String s) {
        return importer.load(s);
    }

    @Override
    public T findElementInCollectionByBinarySearch(List<T> collection, String name) {
        return searcher.search(collection, name);
    }

    @Override
    public List<T> sortCollection(List<T> collection, Comparator<? super T> comparator) {
        return sorter.sort(collection, comparator);
    }
}
