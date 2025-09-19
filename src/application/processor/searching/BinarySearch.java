package application.processor.searching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinarySearch<T> implements SearchStrategy<T> {
    private final Comparator<T> comparator;

    public BinarySearch(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<T> search(List<T> collection, T key) {
        List<T> results = new ArrayList<>();
        if (collection == null || collection.isEmpty()) {
            return results;
        }

        int left = 0, right = collection.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = collection.get(mid);

            int cmp = comparator.compare(midElement, key);

            if (cmp == 0) {
                // нашли совпадение → собираем все влево и вправо
                results.add(midElement);

                // идём влево
                int i = mid - 1;
                while (i >= 0 && comparator.compare(collection.get(i), key) == 0) {
                    results.add(collection.get(i));
                    i--;
                }

                // идём вправо
                i = mid + 1;
                while (i < collection.size() && comparator.compare(collection.get(i), key) == 0) {
                    results.add(collection.get(i));
                    i++;
                }

                break; // собрали всё, можно выходить
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return results;
    }
}
