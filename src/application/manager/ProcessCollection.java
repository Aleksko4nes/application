package application.manager;

import java.util.List;

public interface ProcessCollection<T> {
    // TODO: заполнение коллекции из файла, рандом, вручную;
    List<T> fillCollection(String path);

    // TODO: реализовать бинарный поиск
    T findElementInCollectionByBinarySearch(List<T> collection, String name);

    List<T> sortCollection(List<T> collection);
}
