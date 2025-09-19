package application;

import application.entity.Person;
import application.processor.Processor;
import application.processor.input.InputFromFile;
import application.processor.input.ManualInput;
import application.processor.input.RandomInput;
import application.processor.searching.BinarySearch;
import application.processor.searching.FlexiblePersonComparator;
import application.processor.sorting.*;
import application.processor.utils.PersonParser;

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

                    // case MANUAL_FILLING -> {
                    //     System.out.println("Введите элемент (Имя, Фамилия, Возраст)");
                    //     boolean isStop = false;
                    //     while (!isStop) {
                    //         String value = scanner.nextLine().trim();
                    //         if (value.equalsIgnoreCase("Стоп")) {
                    //             isStop = true;
                    //         }
                    //         if (!isStop && !fillManual(value).isEmpty()) {
                    //             data.add(fillManual(value).getFirst());
                    //             System.out.println("Данные введены: " + data.getLast() + '\n' +
                    //                     "Введи \"Стоп\" если хватит\n");
                    //         }
                    //     }
                    // }

//                    case MANUAL_FILLING -> {
//                        System.out.println(
//                                "Введите элемент (Имя, Фамилия, Возраст)");
//                        boolean isStop = false;
//                        while (!isStop) {
//                            String value = scanner.nextLine().trim();
//                            if (value.equalsIgnoreCase("Стоп")) {
//                                isStop = true;
//                            } else {
//                                List<Person> newPeople = fillManual(value);
//                                if (!newPeople.isEmpty()) {
//                                    Person person = newPeople.get(0);
//                                    data.add(person);
//                                    System.out.println("Данные введены: " + person + '\n' +
//                                            "Введи \"Стоп\" если хватит\n");
//                                }
//                            }
//                        }
//
//                    }

//                    case MANUAL_FILLING -> {
//                        System.out.println(
//                                "Введите элемент (Имя, Фамилия, Возраст)");
//                        boolean isStop = false;
//                        while (!isStop) {
//                            String value = scanner.nextLine().trim();
//                            if (value.equalsIgnoreCase("Стоп")) {
//                                isStop = true;
//                            }
//                            data.add(fillManual(value).getFirst());
//                            System.out.println("Данные введены: " + data + '\n' +
//                                    "Введи \"Стоп\" если хватит\n" );
//                        }
//
//                    }

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
                        System.out.print("Введите имя для поиска: ");
                        String name = scanner.nextLine().trim();

                        System.out.print("Введите фамилию для поиска: ");
                        String lastName = scanner.nextLine().trim();

                        System.out.print("Введите возраст для поиска: ");
                        String ageInput = scanner.nextLine().trim();

                        Integer age = null;
                        if (!ageInput.isEmpty()) {
                            try {
                                age = Integer.parseInt(ageInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Некорректный возраст, поиск будет без него.");
                            }
                        }

                        Person.Builder builder = new Person.Builder();
                        if (!name.isEmpty()) builder.name(name);
                        if (!lastName.isEmpty()) builder.lastName(lastName);
                        if (age != null) builder.age(age);

                        Person template = builder.build();

                        FlexiblePersonComparator comparator = new FlexiblePersonComparator(template);
                        List<Person> found = binarySearch(data, template, comparator);

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

    private static List<Person> binarySearch(List<Person> data, Person key, FlexiblePersonComparator comparator){
        processor.setSearchStrategy(new BinarySearch<>(comparator));
        return processor.findElementInCollectionByBinarySearch(data, key);
    }

    private static List<Person> downloadFromFile (String pathName) {
        processor.setInputStrategy(new InputFromFile<>(new PersonParser()));
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
