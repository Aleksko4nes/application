package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.searching.BinarySearch;
import application.processor.searching.Searcher;

import java.util.List;

public class BinarySearchCommand implements Command {
    private Processor<Person> processor;
    private List<Person> data;
    private String key;
    private List<Person> foundPerson;

    public BinarySearchCommand(Processor<Person> processor, List<Person> data, String key) {
        this.processor = processor;
        this.data = data;
        this.key = key;

    }

    @Override
    public void execute() {
        processor.setSearcher(new Searcher<>(new BinarySearch<>()));
        foundPerson = processor.findElementInCollectionByBinarySearch(data, key);
        System.out.println(foundPerson != null ? "Найдено: " + foundPerson : "Не найдено!");
    }

    @Override
    public void undo() {
        System.out.println("Нельзя отменить поиск.");
    }
}
