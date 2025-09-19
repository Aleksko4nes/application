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
    private OutputStrategy<Person> formatStrategy;

    public UpdateFileOutputCommand(Processor<Person> processor,
                                   List<Person> data,
                                   String path,
                                   OutputStrategy<Person> formatStrategy) {
        this.processor = processor;
        this.data = data;
        this.path = path;
        this.formatStrategy = formatStrategy;
    }

    @Override
    public void execute() {
        UpdateFileOutput<Person> fileOutput = new UpdateFileOutput<>();
        fileOutput.setFormatStrategy(formatStrategy);

        processor.setOutputer(new Outputer<>(fileOutput));
        processor.saveToFile(data, path);
    }

    @Override
    public void undo() {
        System.out.println("Не возможно отменить загрузку.");
    }
}
