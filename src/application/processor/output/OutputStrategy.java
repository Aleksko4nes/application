package application.processor.output;


import java.util.List;

public interface OutputStrategy<T> {
    void downloadFile(List<T> collection, String path);
}
