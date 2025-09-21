package application.processor.output;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class UpdateFileOutput<T> implements OutputStrategy<T> {

    private static final Path RESULT_DIR = resolveDesktopPath().resolve("result");

    public void saveToFile(List<T> data, String filename) {

        if (!filename.toLowerCase().endsWith(".txt")) {
            filename += ".txt";
        }

        Path path = RESULT_DIR.resolve(filename);

        try {
            createDirectory();
            createFileWithBomIfNeeded(path);
            writeDataToFile(data, path);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private void createDirectory() throws IOException {
        if (!Files.exists(RESULT_DIR)) {
            Files.createDirectories(RESULT_DIR);
        }
    }

    private void createFileWithBomIfNeeded(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
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

    private static Path resolveDesktopPath() {
        Path[] possiblePaths = {
                Paths.get(System.getProperty("user.home"), "Рабочий стол"), // для русской Windows
                FileSystemView.getFileSystemView().getHomeDirectory().toPath()
        };

        for (Path path : possiblePaths) {
            if (Files.exists(path) && Files.isDirectory(path)) {
                return path;
            }
        }

        return Paths.get(System.getProperty("user.home"));
    }
}
