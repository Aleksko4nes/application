package application.processor;

import java.util.Comparator;
import java.util.List;

public interface ProcessCollection<T> {

    List<T> fillCollection(String path);

    T findElementInCollectionByBinarySearch(List<T> collection, String name);

    List<T> sortCollection(List<T> collection, Comparator<? super T> comparator);

    void saveToFile(List<T> collection, String path);
}
