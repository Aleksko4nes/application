package application.processor;

import java.util.List;

public interface ProcessCollection<T> {

    List<T> fillCollection(String path);

    List<T> findElementInCollectionByBinarySearch(List<T> collection, T key);

    List<T> sortCollection(List<T> collection);
}
