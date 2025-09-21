package application.processor.output;

import java.io.IOException;
import java.util.List;

public class Outputer<T> {
    private OutputStrategy<T> strategy;

    public Outputer(OutputStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OutputStrategy<T> strategy) {
        this.strategy = strategy;
    }
    
    public void save(List<T> collection, String path) {
        strategy.saveToFile(collection, path);
    }
}
