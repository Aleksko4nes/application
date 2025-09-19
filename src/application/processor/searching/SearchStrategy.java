package application.processor.searching;

import java.util.List;

public interface SearchStrategy<T> {
    List<T> search(List<T> collection, T key);
}
