package application.processor.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSort<T extends Comparable<? super T>> implements SortingStrategy<T> {
    private final ExecutorService executor;
    private final Comparator<? super T> comparator;
    private final int threshold;

    public MergeSort(Comparator<? super T> comparator) {
        this(comparator, Executors.newFixedThreadPool(2), Integer.MAX_VALUE);
    }

    public MergeSort(Comparator<? super T> comparator, ExecutorService executor, int threshold) {
        this.comparator = comparator;
        this.executor = executor;
        this.threshold = threshold;
    }

    @Override
    public List<T> sort(List<T> collection, Comparator<? super T> comparator) {
        if (collection == null || collection.isEmpty()) {
            return collection;
        }
        List<T> result = new ArrayList<>(collection);
        try {
            mergeSort(result, 0, result.size() - 1);
        } finally {
            executor.shutdown();
        }
        return result;
    }

    private void mergeSort(List<T> list, int low, int high) {
        if (low < high) {
            if (high - low <= threshold) {
                list.subList(low, high + 1).sort(comparator);
                return;
            }

            int mid = low + (high - low) / 2;
            try {
                Future<?> leftTask = executor.submit(() -> mergeSort(list, low, mid));
                Future<?> rightTask = executor.submit(() -> mergeSort(list, mid + 1, high));
                leftTask.get();
                rightTask.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Ошибка при выполнении сортировки", e);
            }
            merge(list, low, mid, high);
        }
    }

    private void merge(List<T> list, int low, int mid, int high) {
        List<T> temp = new ArrayList<>(list.subList(low, high + 1));
        int i = low, left = 0, right = mid - low + 1;

        while (left < right && right < temp.size()) {
            if (comparator.compare(temp.get(left), temp.get(right)) <= 0) {
                list.set(i++, temp.get(left++));
            } else {
                list.set(i++, temp.get(right++));
            }
        }

        while (left < right) {
            list.set(i++, temp.get(left++));
        }

        while (right < temp.size()) {
            list.set(i++, temp.get(right++));
        }
    }
}
