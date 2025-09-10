package application;

import application.entity.Person;
import application.processor.Processor;
import application.processor.input.InputFromFile;
import application.processor.input.ManualInput;
import application.processor.input.RandomInput;
import application.processor.searching.BinarySearch;
import application.processor.sorting.*;

import java.util.ArrayList;
import java.util.Comparator;
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

    public static void main(String[] args) {

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
                                "Введите элемент (Имя, Фамилия, Возраст)");
                        boolean isStop = false;
                        while (!isStop) {
                            String value = scanner.nextLine().trim();
                            if (value.equals("Стоп") || value.equals("стоп")) {
                                isStop = true;
                            }
                            data = fillManual(value);
                            System.out.println("Данные введены: " + data + '\n' +
                                    "Введи \"Стоп\" если хватит\n" );
                        }

                    }

                    case SORT_COLLECTION -> {
                        if (data.isEmpty()) {
                           System.out.println("Коллекция пуста. Загрузите данные.");
                           continue;
                        }
                        System.out.println("Критерий сортировки: \n" +
                                "1 - По имени? \n" +
                                "2 - По фамилии? \n" +
                                "3 - Во возрасту?");
                        int sortChoice = Integer.parseInt(scanner.nextLine().trim());
                        Comparator<Person> comparator;
                        switch (sortChoice) {
                            case 1 -> comparator = Comparator.comparing(Person::getName);
                            case 2 -> comparator = Comparator.comparing(Person::getLastName);
                            case 3 -> comparator = Comparator.comparing(Person::getAge);
                            default -> {
                                System.out.println("Не верный выбор!");
                                continue;
                            }
                        }
                        processor.setSortingStrategy(new MergeSort<>(comparator));
                        data = processor.sortCollection(data);
                        System.out.println("Отсортировано: " + data);
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
                    default -> System.out.println("Не верный выбор!");
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
