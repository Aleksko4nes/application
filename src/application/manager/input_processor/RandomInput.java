package application.manager.input_processor;

import application.entity.Person;

import java.util.List;

public class RandomInput<T extends Person> implements InputStrategy<T> {
    @Override
    public List<T> load(String s) {
        return null;//TODO: рандомное заполнение коллекции
    }
}
