package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.output.OutputStrategy;
import application.processor.output.Outputer;
import application.processor.output.UpdateFileOutput;

import java.util.List;

public class UpdateFileOutputCommand implements Command{
    private Processor<Person> processor;
    private List<Person> data;
    private String path;

    public UpdateFileOutputCommand(Processor<Person> processor,
                                   List<Person> data,
                                   String path) {
        this.processor = processor;
        this.data = data;
        this.path = path;
    }

    @Override
    public void execute() {
        processor.setOutputer(new Outputer<>(new UpdateFileOutput<>()));
        processor.saveToFile(data, path);
    }

    @Override
    public void undo() {
        System.out.println("Не возможно отменить загрузку.");
    }
}
