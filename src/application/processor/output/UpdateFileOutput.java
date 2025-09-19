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
import java.util.Collection;
import java.util.List;

public class UpdateFileOutput<T> implements OutputStrategy<T> {

    private OutputStrategy<T> formatStrategy;

    public UpdateFileOutput() {
        // По умолчанию используем TXT
        this.formatStrategy = new TxtSaveStrategy<>();
    }

    public void setFormatStrategy(OutputStrategy<T> formatStrategy) {
        this.formatStrategy = formatStrategy;
    }

    @Override
    public void saveToFile(List<T> data, String filename) throws IOException {
        formatStrategy.saveToFile(data, filename);
    }
}
