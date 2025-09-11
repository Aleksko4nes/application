package application.processor.input;

import application.entity.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static application.processor.input.ManualJsonReader.parsePerson;

public class InputFromFile<T extends Person> implements InputStrategy<Person> {
    @Override
    public List<Person> load(String filename) {
        String fileExtension = getFileExtension(filename);
        return switch (fileExtension) {
            case "txt" -> loadFromTxt(filename);
            case "JSON" -> loadFromJSON(filename);
            default -> new ArrayList<>();
        };
    }

    private List<Person> loadFromTxt(String filename) {
        try {
            List<Person> persons = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                try {
                    // Предполагаем формат: Name,Age,Email
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Person person = new Person.Builder()
                                .name(parts[0].trim())
                                .lastName(parts[1].trim())
                                .age(Integer.parseInt(parts[2]))
                                .build();
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

    private List<Person> loadFromJSON(String filename) {
        List<Person> persons = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line.trim());
            }

            String json = content.toString();
            // Упрощенный парсинг JSON массива
            if (json.startsWith("[") && json.endsWith("]")) {
                String objects = json.substring(1, json.length() - 1);
                String[] personArray = objects.split("\\},\\s*\\{");

                for (String personStr : personArray) {
                    Person person = parsePerson(personStr.replace("{", "").replace("}", ""));
                    if (person != null) {
                        persons.add(person);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return persons;
    }

    private String getFileExtension(String fileName) {
        return fileName.split("\\.")[1];
    }
}
