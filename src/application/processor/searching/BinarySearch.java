package application.processor.searching;

import application.entity.Person;

import java.util.List;

public class BinarySearch<T> implements SearchStrategy<T>{
    private final String searchField = "name";
    @Override
    public T search(List<T> collection, String key) {
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
