package application.processor;

import java.util.List;

public interface ProcessCollection<T> {

    List<T> fillCollection(String path);

    T findElementInCollectionByBinarySearch(List<T> collection, String name);

    List<T> sortCollection(List<T> collection);
}
