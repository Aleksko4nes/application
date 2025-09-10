package application.manager.searching;

import java.util.List;

public interface SearchStrategy<T> {
    T search(List<T> collection, String name);
}
