package application.processor.searching;

import application.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BinarySearch<T> implements SearchStrategy<T> {
        private final String searchField = "name";
    @Override
    public T search(List<T> collection, String key) {

//            if (collection == null || collection.isEmpty()) {
//                return null;
//            }
//
//            int numThreads = 4;
//            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//
//            try {
//                int chunkSize = (int) Math.ceil((double) collection.size() / numThreads);
//                List<Future<Integer>> futures = new ArrayList<>();
//
//                for (int i = 0; i < collection.size(); i += chunkSize) {
//                    int start = i;
//                    int end = Math.min(i + chunkSize, collection.size());
//                    futures.add(executor.submit(() -> countInChunk(collection.subList(start, end), key)));
//                }
//
//                int totalCount = 0;
//                for (Future<Integer> future : futures) {
//                    totalCount += future.get();
//                }
//                System.out.println("Количество элементов в списке: " + totalCount);
//
//            } catch (InterruptedException | ExecutionException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException("Ошибка при подсчёте вхождений", e);
//
//            } finally {
//                executor.shutdown();
//            }
//            return null;
//        }
//
//        private int countInChunk(List<T> chunk, String element) {
//            int count = 0;
//            for (T item : chunk) {
//                if (item.equals(element)) {
//                    count++;
//                }
//            }
//            return count;
//        }
//    }

        if (collection == null || collection.isEmpty()) {
            return null;
        }

        int left = 0;
        int right = collection.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = collection.get(mid);

            int comparison = compareWithKey(midElement, key);

            if (comparison == 0) {
                return midElement;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    private int compareWithKey(T element, String key) {
        if (element instanceof Person) {
            Person person = (Person) element;
            switch (searchField.toLowerCase()) {
                case "name":
                    return person.getName().compareTo(key);
                case "lastname":
                    return person.getLastName().compareTo(key);
                case "age":
                    try {
                        return Integer.compare(person.getAge(), Integer.parseInt(key));
                    } catch (NumberFormatException e) {
                        return -1; // если возраст не число
                    }
                default:
                    return person.getName().compareTo(key);
            }
        }
        return element.toString().compareTo(key);
    }
    }