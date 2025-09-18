package application.processor.sorting;

import java.util.Comparator;
import java.util.List;

public interface SortingStrategy<T> {
    List<T> sort(List<T> collection, Comparator<? super T> comparator);
}

