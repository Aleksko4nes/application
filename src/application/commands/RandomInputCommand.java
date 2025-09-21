package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.input.Importer;
import application.processor.input.RandomInput;

import java.util.ArrayList;
import java.util.List;

public class RandomInputCommand implements Command {
    private Processor<Person> processor;
    private String size;
    private List<Person> data;
    private List<Person> previousData;

    public RandomInputCommand(Processor<Person> processor, String size, List<Person> data) {
        this.processor = processor;
        this.size = size;
        this.data = data;
        this.previousData = new ArrayList<>(data);
    }

    @Override
    public void execute() {
        processor.setImporter(new Importer<>(new RandomInput<>()));
        List<Person> newData = processor.fillCollection(size);
        data.clear();
        data.addAll(newData);
        System.out.println("Данные сгенерированы: " + data);
    }

    @Override
    public void undo() {
        data.clear();
        data.addAll(previousData);
        System.out.println("Генерация данных отменена. Восстановлено: " + data);
    }
}
