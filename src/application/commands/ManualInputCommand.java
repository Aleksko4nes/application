package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.input.Importer;
import application.processor.input.ManualInput;

import java.util.List;

public class ManualInputCommand implements Command {
    private Processor<Person> processor;
    private String value;
    private List<Person> data;
    private Person addedPerson;

    public ManualInputCommand(Processor<Person> processor, String value, List<Person> data) {
        this.processor = processor;
        this.value = value;
        this.data = data;
    }

    @Override
    public void execute() {
        processor.setImporter(new Importer<>(new ManualInput<>()));
        List<Person> newData = processor.fillCollection(value);
        if (!newData.isEmpty()) {
            addedPerson = newData.get(0);
            data.add(addedPerson);
            System.out.println("Данные введены: " + data);
        }
    }

    @Override
    public void undo() {
        if (addedPerson != null && data.remove(addedPerson)) {
            System.out.println("Отмена добавления: " + addedPerson);
        }
    }
}
