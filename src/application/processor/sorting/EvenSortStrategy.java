package application.processor.sorting;

import java.util.*;
import java.util.function.Function;

public class EvenSortStrategy<T extends Comparable<? super T>> implements SortingStrategy<T> {

    private final Function<T, Integer> keyExtractor;
    private final Comparator<? super T> comparator;

    public EvenSortStrategy(Function<T, Integer> keyExtractor, Comparator<? super T> comparator) {
        this.keyExtractor = keyExtractor;
        this.comparator = comparator != null ? comparator : Comparator.naturalOrder();
    }

    @Override
    public List<T> sort(List<T> collection, Comparator<? super T> ignored) {
        List<T> result = new ArrayList<>(collection);
        List<T> evens = new ArrayList<>();
        for (T item : result) {
            Integer key = keyExtractor.apply(item);
            if (key != null && key % 2 == 0) evens.add(item);
        }
        evens.sort(comparator);
        Iterator<T> it = evens.iterator();
        for (int i = 0; i < result.size() && it.hasNext(); i++) {
            Integer key = keyExtractor.apply(result.get(i));
            if (key != null && key % 2 == 0) {
                result.set(i, it.next());
            }
        }
        return result;
    }
}