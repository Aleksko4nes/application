package application.commands;

import application.entity.Person;
import application.processor.Processor;
import application.processor.searching.BinarySearch;
import application.processor.searching.Searcher;

import java.util.List;

public class BinarySearchCommand implements Command {
    private Processor<Person> processor;
    private List<Person> data;
    private String name;
    private Person foundPerson;

    public BinarySearchCommand(Processor<Person> processor, List<Person> data, String name) {
        this.processor = processor;
        this.data = data;
        this.name = name;
    }

    @Override
    public void execute() {
        processor.setSearcher(new Searcher<>(new BinarySearch<>()));
        foundPerson = processor.findElementInCollectionByBinarySearch(data, name);
        System.out.println(foundPerson != null ? "Найдено: " + foundPerson : "Не найдено!");
    }

    @Override
    public void undo() {
        System.out.println("Нельзя отменить поиск.");
    }

    public Person getFoundPerson() {
        return foundPerson;
    }
}
