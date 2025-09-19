package application.processor.input;

import application.entity.Person;
import application.processor.input.InputStrategy;
import application.processor.utils.DataParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class InputFromFile<T extends Person> implements InputStrategy<T> {
    private final DataParser<T> parser;

    public InputFromFile(DataParser<T> parser) {
        this.parser = parser;
    }

    @Override
    public List<T> load(String filename) {
        try {
            String fileExtension = getFileExtension(filename);
            return switch (fileExtension.toLowerCase()) {
                case "txt" -> loadFromTxt(filename);
                case "json" -> loadFromJSON(filename);
                default -> List.of();
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    private List<T> loadFromTxt(String filename) {
        try {
            return Files.lines(Paths.get(filename))
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .filter(line -> {
                        String[] parts = line.split("\\s+");
                        if (parts.length != 3) {
                            System.out.println("Пропуск невалидной строки: " + line);
                            return false;
                        }
                        return true;
                    })
                    .map(line -> {
                        try {
                            return parser.parseFromString(line);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Пропуск невалидных данных: " + line + " - " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(item -> item != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> loadFromJSON(String filename) {
        List<T> items = new java.util.ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filename))) {
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

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}