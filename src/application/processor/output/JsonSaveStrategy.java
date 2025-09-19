package application.processor.output;

import application.entity.Person;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class JsonSaveStrategy<T> implements OutputStrategy<T> {
    @Override
    public void saveToFile(List<T> data, String filename) throws IOException {
        // Добавляем расширение .json если его нет
        if (!filename.toLowerCase().endsWith(".json")) {
            filename += ".json";
        }

        Path path = Paths.get(filename).toAbsolutePath();

        // Создаем директорию если нужно
        createParentDirectory(path);

        // Записываем данные
        writeDataToFile(data, path);
    }

    private String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        // Если объект - строка, экранируем кавычки
        if (obj instanceof String) {
            return "\"" + escapeJsonString((String) obj) + "\"";
        }

        // Если объект - число, boolean или null
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }

        // Если объект - Person
        if (obj instanceof Person) {
            return personToJson((Person) obj);
        }

        // Для других объектов используем toString (можно доработать)
        return "\"" + escapeJsonString(obj.toString()) + "\"";
    }

    private String personToJson(Person person) {
        return "{" +
                "\"name\":\"" + escapeJsonString(person.getName()) + "\"," +
                "\"lastName\":\"" + escapeJsonString(person.getLastName()) + "\"," +
                "\"age\":" + person.getAge() +
                "}";
    }

    private String escapeJsonString(String str) {
        if (str == null) return "";

        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void createParentDirectory(Path path) throws IOException {
        Path parentDir = path.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
    }

    private void writeDataToFile(List<T> data, Path path) throws IOException {
        boolean fileExists = Files.exists(path);

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            if (!fileExists || Files.size(path) == 0) {
                writer.write("[\n"); // Начало массива для нового файла
            } else {
                // Для существующего файла удаляем последнюю скобку и добавляем запятую
                long size = Files.size(path);
                String content = Files.readString(path);
                content = content.substring(0, content.length() - 1) + ",\n";
                Files.writeString(path, content);
            }

            for (int i = 0; i < data.size(); i++) {
                T item = data.get(i);
                writer.write("  " + toJson(item));

                if (i < data.size() - 1) {
                    writer.write(",");
                }
                writer.newLine();
            }

            writer.write("]"); // Конец массива JSON
        }
    }
}
