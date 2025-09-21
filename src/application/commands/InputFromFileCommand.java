package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.input.Importer;
import application.processor.input.InputFromFile;
import application.processor.utils.PersonParser;

import java.util.ArrayList;
import java.util.List;

public class InputFromFileCommand implements Command {
    private Processor<Person> processor;
    private String path;
    private List<Person> data;
    private List<Person> previousData;

    public InputFromFileCommand(Processor<Person> processor, String path, List<Person> data) {
        this.processor = processor;
        this.path = path;
        this.data = data;
        this.previousData = new ArrayList<>(data);
    }

    @Override
    public void execute() {
        processor.setImporter(new Importer<>(new InputFromFile<>(new PersonParser())));
        List<Person> newData = processor.fillCollection(path);
        data.clear();
        data.addAll(newData);
        System.out.println("Данные загружены: " + data);
    }

    @Override
    public void undo() {
        data.clear();
        data.addAll(previousData);
        System.out.println("Отмена загрузки данных. Восстановлено: " + data);
    }
}
