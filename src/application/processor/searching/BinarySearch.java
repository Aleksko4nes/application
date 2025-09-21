package application.processor.searching;

import application.entity.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinarySearch<T extends Person> implements SearchStrategy<T> {

    @Override
    public List<T> search(List<T> collection, String key) {
        List<T> results = new ArrayList<>();
        collection.sort(Comparator.naturalOrder());

        if (collection == null || collection.isEmpty()) {
            System.out.println("Ничего не нашлось");
            return results;
        }

        if (key.matches(".*\\d.*")) {
            System.out.println("Неверный формат");
            return results;
        }

        String[] parts = key.trim().split("\\s+");
        String name = parts[0];
        String lastName = (parts.length > 1) ? parts[1] : null;

        int left = 0;
        int right = collection.size() - 1;
        boolean found = false;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            T midElement = collection.get(mid);

            if (!(midElement instanceof Person)) {
                break;
            }

            Person person = midElement;
            int cmp = compare(name, lastName, person);

            if (cmp == 0) {
                results.add(midElement);

                int i = mid - 1;
                while (i >= left) {
                    Person p = collection.get(i);
                    if (compare(name, lastName, p) == 0) {
                        results.add((T) p);
                        i--;
                    } else {
                        break;
                    }
                }

                i = mid + 1;
                while (i <= right) {
                    Person p = collection.get(i);
                    if (compare(name, lastName, p) == 0) {
                        results.add((T) p);
                        i++;
                    } else {
                        break;
                    }
                }

                found = true;
                break;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (!found) {
            System.out.println("Ничего не нашлось");
        }

        return results;
    }

    private int compare(String name, String lastName, Person person) {
        String personName = person.getName().trim().toLowerCase();
        String searchName = name.trim().toLowerCase();

        int cmp = personName.compareTo(searchName);

        if (cmp == 0 && lastName != null) {
            String personLastName = person.getLastName().trim().toLowerCase();
            String searchLastName = lastName.trim().toLowerCase();
            cmp = personLastName.compareTo(searchLastName);
        }
        return cmp;
    }
}
