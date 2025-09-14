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
                        
                        System.out.println("По какому полю искать? \n1 - По имени\n2 - По фамилии\n3 - По возрасту");
                        int searchChoice = Integer.parseInt(scanner.nextLine().trim());
                        
                        String searchField;
                        switch (searchChoice) {
                            case 1 -> searchField = "name";
                            case 2 -> searchField = "lastname";
                            case 3 -> searchField = "age";
                            default -> {
                                System.out.println("Неверный выбор! Будет использовано имя.");
                                searchField = "name";
                            }
                        }
                         
                        if (!isCollectionSorted(data, searchField)) {
                            System.out.println("Внимание: коллекция не отсортирована по полю '" + searchField + "'!");
                            System.out.println("Бинарный поиск требует отсортированной коллекции.");
                            System.out.println("1 - Сначала отсортировать\n2 - Продолжить anyway\n3 - Отменить поиск");
                            
                            int sortDecision = Integer.parseInt(scanner.nextLine().trim());
                            switch (sortDecision) {
                                case 1 -> {
                                    Comparator<Person> comparator;
                                    switch (searchField) {
                                        case "name" -> comparator = Comparator.comparing(Person::getName);
                                        case "lastname" -> comparator = Comparator.comparing(Person::getLastName);
                                        case "age" -> comparator = Comparator.comparing(Person::getAge);
                                        default -> comparator = Comparator.comparing(Person::getName);
                                    }
                                    processor.setSortingStrategy(new MergeSort<>(comparator));
                                    data = processor.sortCollection(data);
                                    System.out.println("Коллекция отсортирована: " + data);
                                }
                                case 2 -> {
                                    System.out.println("Продолжаем поиск (результат может быть некорректным)...");
                                }
                                case 3 -> {
                                    System.out.println("Поиск отменен.");
                                    continue;
                                }
                                default -> {
                                    System.out.println("Неверный выбор! Поиск отменен.");
                                    continue;
                                }
                            }
                        }
                        
                        System.out.println("Введите значение для поиска: ");
                        String searchValue = scanner.nextLine().trim();
                        
                        BinarySearch<Person> binarySearch = new BinarySearch<>();
                        binarySearch.setSearchField(searchField);
                        processor.setSearchStrategy(binarySearch);
                        
                        Person found = binarySearch(data, searchValue);
                        if (found != null) {
                            System.out.println("===Найдено:===");
                            System.out.println("   Имя: " + found.getName());
                            System.out.println("   Фамилия: " + found.getLastName());
                            System.out.println("   Возраст: " + found.getAge());
                        } else {
                            System.out.println("===Не найдено!===");
                        }

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

    private static boolean isCollectionSorted(List<Person> collection, String field) {
    if (collection == null || collection.size() <= 1) {
        return true;
    }
    
    for (int i = 0; i < collection.size() - 1; i++) {
        Person current = collection.get(i);
        Person next = collection.get(i + 1);
        
        int comparison;
        switch (field.toLowerCase()) {
            case "name":
                comparison = current.getName().compareTo(next.getName());
                break;
            case "lastname":
                comparison = current.getLastName().compareTo(next.getLastName());
                break;
            case "age":
                comparison = Integer.compare(current.getAge(), next.getAge());
                break;
            default:
                comparison = current.getName().compareTo(next.getName());
        }
        
        if (comparison > 0) {
            return false;
        }
    }
    return true;
}

}
