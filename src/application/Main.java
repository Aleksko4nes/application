package application;

import application.entity.Person;
import application.manager.ProcessCollection;
import application.manager.Processor;
import application.manager.input_processor.InputFromFile;
import application.manager.input_processor.ManualInput;
import application.manager.input_processor.RandomInput;
import application.manager.searching.BinarySearch;
import application.manager.sorting.SortingByAge;
import application.manager.sorting.SortingByLastName;
import application.manager.sorting.SortingByName;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int INPUT_FROM_FILE = 1;
    private static final int RANDOM_FILLING = 2;
    private static final int MANUAL_FILLING = 3;
    private static final int SORT_COLLECTION = 4;
    private static final int FIND_IN_COLLECTION = 5;
    private static final int EXIT = 6;

    private static Processor<Person> processor = new Processor<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Person> data = new ArrayList<>();


    public static void main(String[] args) throws InterruptedException {

        System.out.println("Добро пожаловать!");

        while (true) {
            System.out.println("""
                    Выберите действие:
                    1 - Загрузить из файла
                    2 - Заполнить случайными значениями
                    3 - Заполнить вручную
                    4 - Отсортировать коллекцию
                    5 - Найти элемент
                    6 - Выход""");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case INPUT_FROM_FILE -> {
                        System.out.println("Введите путь к файлу: ");
                        String path = scanner.nextLine().trim();
                        data = downloadFromFile(path);
                        System.out.println("Данные загружены: " + data);
                    }

                    case RANDOM_FILLING -> {
                        System.out.println("Введите колличество элементов: ");
                        String size = scanner.nextLine().trim();
                        data = randomFilling(size);
                        System.out.println("Данные сгенерированы: " + data);
                    }

                    case MANUAL_FILLING -> {
                        System.out.println(
                                "Введите элемент: " + '\n' +
                                        "(Имя, Фамилия, Возраст)");
                        String value = scanner.nextLine().trim();
                        data = fillManual(value);
                        System.out.println("Данные введены: " + data);
                    }

                    case SORT_COLLECTION -> {
//                        if (data.isEmpty()) {
//                            System.out.println("Коллекция пуста. Загрузите данные.");
//                            continue;
//                        }
                        System.out.println("Критерий сортировки: \n" +
                                "1 - По имени? \n" +
                                "2 - По фамилии? \n" +
                                "3 - Во возрасту?");
                        int sortChoice = Integer.parseInt(scanner.nextLine().trim());
                        switch (sortChoice) {
                            case 1:
                                data = sortByName(data);
                                System.out.println("Отсортировано по имени: " + data);
                                continue;
                            case 2:
                                data = sortByLastName(data);
                                System.out.println("Отсортировано по фамилии: " + data);
                                continue;
                            case 3:
                                data = sortByAge(data);
                                System.out.println("Отсортировано по возрасту: " + data);
                                continue;
                            default:
                                System.out.println("Не верный выбор!");
                        }
                    }
                    case FIND_IN_COLLECTION -> {
                        if (data.isEmpty()) {
                            System.out.println("Коллекция пуста. Загрузите данные.");
                            continue;
                        }
                        System.out.println("Введите имя для поиска: ");
                        String name = scanner.nextLine().trim();
                        Person found = binarySearch(data, name);
                        System.out.println(found != null ? "Найдено: " + found : "Не найдено!");
                    }
                    case EXIT -> {
                        System.out.println("Выход...");
                        scanner.close();
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static Person binarySearch(List<Person> data, String name){
        processor.setSearchStrategy(new BinarySearch<>());
        return processor.findElementInCollectionByBinarySearch(data, name);
    }

    private static List<Person> sortByName (List<Person> data) {
        processor.setSortingStrategy(new SortingByName<>());
        return processor.sortCollection(data);
    }

    private static List<Person> sortByLastName(List<Person> data) {
        processor.setSortingStrategy(new SortingByLastName<>());
        return processor.sortCollection(data);
    }

    private static List<Person> sortByAge (List<Person> data) {
        processor.setSortingStrategy(new SortingByAge<>());
        return processor.sortCollection(data);
    }

    private static List<Person> downloadFromFile (String pathName) {
        processor.setInputStrategy(new InputFromFile<>());
        return processor.fillCollection(pathName);
    }

    private static List<Person> randomFilling(String size) {
        processor.setInputStrategy(new RandomInput<>());
        return processor.fillCollection(size);
    }

    private static List<Person> fillManual(String value) {
        processor.setInputStrategy(new ManualInput<>());
        return processor.fillCollection(value);
    }
}
