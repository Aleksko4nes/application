package application.manager.input_processor;

import application.entity.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputFromFile<T extends Person> implements InputStrategy<Person> {
    @Override
    public List<Person> load(String filename) {
        try {
            List<Person> persons = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                try {
                    // Предполагаем формат: Name,Age,Email
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Person person = DataValidator.createValidatedPerson(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim()
                        );
                        persons.add(person);
                    } else {
                        System.out.println("Skipping invalid line: " + line);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid data: " + line + " - " + e.getMessage());
                }
            }

            Person[] dataArray = persons.toArray(new Person[0]);

            return Arrays.asList(dataArray);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
