package application.processor.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileService {

    private static final String RESULTS_FILE = "search_results.txt";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Метод для записи результата поиска в файл (режим добавления)

    public static void writeSearchResultToFile(Object result, String searchKey) {
        Path path = Paths.get(RESULTS_FILE);

        try {
            // Создаем файл, если не существует
            if (!Files.exists(path)) {
                Files.createFile(path);
                if (Files.size(path) == 0) {
                    // Добавляем BOM для корректного отображения в Notepad
                    try (BufferedWriter writer = Files.newBufferedWriter(
                            path,
                            StandardCharsets.UTF_8,
                            StandardOpenOption.WRITE
                    )) {
                        writer.write("\uFEFF"); // UTF-8 BOM
                    }
                }
            }

            // Формируем запись
            String record = String.format(
                    "[%s] Поиск: '%s' -> Результат: %s%n",
                    LocalDateTime.now().format(formatter),
                    searchKey,
                    result != null ? result.toString() : "не найден"
            );

            // Записываем в режиме добавления с указанием кодировки UTF-8
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND
            )) {
                writer.write(record);
                writer.flush();
            }

            System.out.println("Результат поиска сохранен в файл: " + RESULTS_FILE);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    // Метод для чтения всех результатов из файла
    public static void printSearchResults() {
        try {
            Path path = Paths.get(RESULTS_FILE);
            if (Files.exists(path)) {
                String content = Files.readString(path);
                System.out.println("\nИстория поиска:");
                System.out.println(content);
            } else {
                System.out.println("Файл с результатами поиска не существует.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
