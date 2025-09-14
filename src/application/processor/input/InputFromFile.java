package application.processor.input;

import application.entity.Person;
import application.utils.DataParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputFromFile<T extends Person> implements InputStrategy<T> {
    private final DataParser<T> parser;

    public InputFromFile(DataParser<T> parser) {
        this.parser = parser;
    }
    @Override
    public List<T> load(String filename) {
        String fileExtension = getFileExtension(filename);
        return switch (fileExtension.toLowerCase()) {
            case "txt" -> loadFromTxt(filename);
            case "json" -> loadFromJSON(filename);
            default -> new ArrayList<>();
        };
    }
    // убрать try catch
    private List<T> loadFromTxt(String filename) {
        try {
            List<T> items = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                try {
                    // Предполагаем формат: Name,LastName,Age
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        T item = parser.parseFromString(line);
                        items.add(item);
                    } else {
                        System.out.println("Пропуск невалидной строки: " + line);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Пропуск невалидных данных: " + line + " - " + e.getMessage());
                }
            }

            return items;

        } catch (IOException e) {
            System.out.println("Ошибка файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private List<T> loadFromJSON(String filename) {
        List<T> items = new ArrayList<>();

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
                    String cleanedStr = personStr.replace("{", "").replace("}", "");
                    T item = parser.parseFromJson(cleanedStr);
                    items.add(item);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Находит последнюю точку в пути и возвращает расширение файла
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}
