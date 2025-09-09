package application.manager;

import java.util.List;

public interface ProcessCollection<T> {
    // TODO: заполнение коллекции из файла, рандом, вручную;
    List<T> fillCollection(T t);

    // TODO: реализовать бинарный поиск
    T findElementInCollectionByBinarySearch(String name);



}
