package application.processor.searching;

import java.util.List;

public interface SearchStrategy<T> {
    T search(List<T> collection, String name);
}
