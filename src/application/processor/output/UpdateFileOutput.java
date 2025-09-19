package application.processor.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UpdateFileOutput<T> implements OutputStrategy<T> {

    private static final String RESULTS_FILE = "search_results.txt";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void downloadFile(List<T> collection, String path) {
        //TODO: Сделать метод, который принимает коллекцию и !имя файла!. Директория для сохранения - константа
        // если имя новое - то создаём новый файл.
        // если имя уже есть - то записываем новые файлы дальше.


        Path path = Paths.get(RESULTS_FILE);

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                if (Files.size(path) == 0) {
                    try (BufferedWriter writer = Files.newBufferedWriter(
                            path,
                            StandardCharsets.UTF_8,
                            StandardOpenOption.WRITE
                    )) {
                        writer.write("\uFEFF"); // UTF-8 BOM
                    }
                }
            }

            String record = String.format(
                    "[%s] Поиск: '%s' -> Результат: %s%n",
                    LocalDateTime.now().format(formatter),
                    searchKey,
                    result != null ? result.toString() : "не найден"
            );

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
}
