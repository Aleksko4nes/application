package application.manager.sorting;

import java.util.List;

public interface SortingStrategy<T> {
    List<T> sort(List<T> collection);

}
