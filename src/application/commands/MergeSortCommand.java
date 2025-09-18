package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.sorting.MergeSort;
import application.processor.sorting.Sorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSortCommand implements Command {
    private Processor<Person> processor;
    private List<Person> data;
    private Comparator<Person> comparator;
    private List<Person> previousData;

    public MergeSortCommand(Processor<Person> processor, List<Person> data, Comparator<Person> comparator) {
        this.processor = processor;
        this.data = data;
        this.comparator = comparator;
        this.previousData = new ArrayList<>(data);
    }

    @Override
    public void execute() {
        processor.setSorter(new Sorter<>(new MergeSort<>(comparator)));
        List<Person> sortedList = processor.sortCollection(data, comparator);
        data.clear();
        data.addAll(sortedList);
        System.out.println("Список отсортирован: " + data);
    }

    @Override
    public void undo() {
        data.clear();
        data.addAll(previousData);
        System.out.println("Отмена сортировки. Восстановлено " + data);
    }
}
