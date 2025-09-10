package application.processor.sorting;

import java.util.Comparator;
import java.util.List;

public interface SortingStrategy<T> {
    List<T> sort(List<T> collection, Comparator<? super T> comparator);

    default List<T> sort(List<T> collection) {
        return sort(collection, (Comparator<? super T>) Comparator.naturalOrder());
    }

}
