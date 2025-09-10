package application.processor.input;

import application.entity.Person;

import java.util.List;

public class Importer<T extends Person> {
    private InputStrategy<T> strategy;

    public void setStrategy(InputStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public Importer(InputStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public List<T> load(String s) {
        return strategy.load(s);
    }
}
