package application.processor.output;

import java.util.List;

public class Outputer<T> {
    private OutputStrategy<T> strategy;

    public Outputer(OutputStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OutputStrategy<T> strategy) {
        this.strategy = strategy;
    }
    
    public void download(List<T> collection, String path) {
        strategy.downloadFile(collection, path);
    }
}
