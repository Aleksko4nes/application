package application.processor.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TxtSaveStrategy<T> implements OutputStrategy<T> {
    @Override
    public void saveToFile(List<T> data, String filename) throws IOException {
        // Добавляем расширение .txt если его нет
        if (!filename.toLowerCase().endsWith(".txt")) {
            filename += ".txt";
        }

        Path path = Paths.get(filename).toAbsolutePath();

        // Создаем директорию если нужно
        createParentDirectory(path);

        // Создаем файл с BOM если не существует
        createFileWithBomIfNeeded(path);

        // Записываем данные
        writeDataToFile(data, path);
    }

    private void createParentDirectory(Path path) throws IOException {
        Path parentDir = path.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
    }

    private void createFileWithBomIfNeeded(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
            // Добавляем BOM только для новых файлов
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE
            )) {
                writer.write("\uFEFF"); // UTF-8 BOM
            }
        }
    }

    private void writeDataToFile(List<T> data, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.APPEND
        )) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
    }
}
