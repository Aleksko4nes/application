package application.processor.output;


import java.io.IOException;
import java.util.List;

public interface OutputStrategy<T> {
    void saveToFile(List<T> collection, String path) throws IOException;
}
