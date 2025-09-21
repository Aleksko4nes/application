package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.sorting.EvenSortStrategy;
import application.processor.sorting.Sorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenSortCommand implements Command {
    private Processor<Person> processor;
    private List<Person> data;;
    private List<Person> previousData;
    private Comparator<Person> comparator;

    public EvenSortCommand(Processor<Person> processor, List<Person> data) {
        this.processor = processor;
        this.data = data;
        this.previousData = new ArrayList<>(data);
    }

    @Override
    public void execute() {
        processor.setSorter(new Sorter<>(new EvenSortStrategy<>(Person::getAge, Comparator.comparing(Person::getAge))));
        List<Person> sortedData = processor.sortCollection(data, comparator);
        data.clear();
        data.addAll(sortedData);
        System.out.println("Отсортированная коллекция: " + data);
    }

    @Override
    public void undo() {
        data.clear();
        data.addAll(previousData);
        System.out.println("Сортировка отменена! " + data);
    }
}
