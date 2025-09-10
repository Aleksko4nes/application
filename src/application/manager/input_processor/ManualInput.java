package application.manager.input_processor;

import application.entity.Person;

import java.util.List;

public class ManualInput<T extends Person> implements InputStrategy<T>{
    @Override
    public List<T> load(String value) {
        return null;
        //TODO: Реализовать метод мануального создания коллекции из консоли
    }
}
