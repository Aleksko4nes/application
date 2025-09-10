package application.processor.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSort<T extends Comparable<? super T>> implements SortingStrategy<T>{
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final Comparator<? super T> comparator;

    public MergeSort(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<T> sort(List<T> collection, Comparator<? super T> comparator) {
        if (collection == null || collection.isEmpty()) {return collection;}
        List<T> result = new ArrayList<>(collection);
        try {
            mergeSort(result, 0, result.size()-1, comparator);
        } finally {
            executor.shutdown();
        }
        return result;
    }

    private void mergeSort(List<T> list, int low, int high, Comparator<? super T> comparator){
        if (low < high) {
            int mid = low + (high - low) / 2;
            try {
                Future<?> left = executor.submit(()-> mergeSort(list, low, mid, comparator));
                Future<?> right = executor.submit(() -> mergeSort(list, mid+1, high, comparator));
                left.get();
                right.get();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            merge(list, low, mid, high, comparator);
        }
    }

    private void merge(List<T> list, int low, int mid, int high, Comparator<? super T> comparator) {
        List<T> temp = new ArrayList<>(list.subList(low, high+1));
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
    }
}
