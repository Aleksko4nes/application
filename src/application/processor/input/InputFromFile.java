package application.processor.input;

import application.entity.Person;
import application.processor.utils.DataParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            String fileExtension = getFileExtension(filename);
            return switch (fileExtension.toLowerCase()) {
                case "txt" -> loadFromTxt(filename);
                case "json" -> loadFromJSON(filename);
                default -> new ArrayList<>();
            };
        } else {
            System.out.println("Файл не существует.");
            return new ArrayList<>();
        }
    }
    private List<T> loadFromTxt(String filename) {
        try {
            List<T> items = new ArrayList<>();
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                try {
                    // Убираем лишние пробелы и нормализуем строку
                    String normalizedLine = line.trim().replaceAll("\\s+", " ");
                    // Используем регулярное выражение для гибкого парсинга
                    // Разрешаем: запятые, точки, пробелы, точки с запятой как разделители
                    String[] parts = normalizedLine.split("[,;.\\s]+");

                    if (parts.length == 3) {
                        T item = parser.parseFromString(line);
                        items.add(item);
                    } else {
                        System.out.println("Пропуск невалидной строки (должно быть 3 компонента): " + line);
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
