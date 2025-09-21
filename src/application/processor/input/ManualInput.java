package application.processor.input;

import application.entity.Person;

import java.util.Arrays;
import java.util.List;

public class ManualInput<T extends Person> implements InputStrategy<T>{
    @Override
    public List<T> load(String value) {
        if (value == null || value.trim().isEmpty()) {
            System.out.println("Пустая строка!");
            return List.of();
        }

        String[] personalData = value.trim().split("\\s+");

        return Arrays.stream(new String[][]{personalData})
                .filter(arr -> arr.length == 3)
                .filter(arr -> arr[2].matches("\\d+"))
                .map(arr -> {
                    int age = Integer.parseInt(arr[2]);
                    @SuppressWarnings("unchecked")
                    T person = (T) new Person.Builder()
                            .name(arr[0])
                            .lastName(arr[1])
                            .age(age)
                            .build();
                    return person;
                })
                .peek(_ -> {})
                .toList();
    }
}
