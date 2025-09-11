package application.processor.input;

import application.entity.Person;

import java.util.List;

public class InputFromFile<T extends Person> implements InputStrategy<T> {
    @Override
    public List<T> load(String size) {
        return null;
        //TODO: Реализовать логику загрузки из файла
    }
}
