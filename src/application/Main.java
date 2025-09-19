package application;

import application.commands.*;
import application.entity.Person;
import application.processor.Processor;

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
    private static final int EVEN_SORT = 6;
    private static final int EXIT = 7;
    private static final int UNDO = 8;

    private static final CommandInvoker commandInvoker = new CommandInvoker();
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Person> data = new ArrayList<>();
    private static Processor<Person> processor = new Processor<>();

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
                    6 - Сортировка четных значений
                    7 - Выход
                    8 - Отменить последнюю команду""");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case INPUT_FROM_FILE -> {
                        System.out.println("Введите путь к файлу: ");
                        String path = scanner.nextLine().trim();
                        Command inputCommand = new InputFromFileCommand(processor, path, data);
                        commandInvoker.executeCommand(inputCommand);
                    }

                    case RANDOM_FILLING -> {
                        System.out.println("Введите колличество элементов: ");
                        String size = scanner.nextLine().trim();
                        Command inputCommand = new RandomInputCommand(processor, size, data);
                        commandInvoker.executeCommand(inputCommand);
                    }

                    case MANUAL_FILLING -> {
                        System.out.println("Введите элемент (Имя, Фамилия, Возраст)");
                        boolean isStop = false;
                        while (!isStop) {
                            String value = scanner.nextLine().trim();
                            if (value.equalsIgnoreCase("Стоп")) {
                                isStop = true;
                            } else {
                                Command inputCommand = new ManualInputCommand(processor, value, data);
                                commandInvoker.executeCommand(inputCommand);
                                System.out.println("Введите \"Стоп\" если хватит\n");
                            }
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
                        Command command = new MergeSortCommand(processor,data,comparator);
                        commandInvoker.executeCommand(command);
                    }
                    case FIND_IN_COLLECTION -> {
                                if (data.isEmpty()) {
                                    System.out.println("Коллекция пуста. Загрузите данные.");
                            continue;
                        }
                        System.out.println("Введите имя для поиска: ");
                        String name = scanner.nextLine().trim();
                        Command command = new BinarySearchCommand(processor, data, name);
                        commandInvoker.executeCommand(command);
                    }

                    case EVEN_SORT -> {
                        if (data.isEmpty()) {
                            System.out.println("Коллекция пустая! Загрузите данные.");
                        } else {
                            Command command = new EvenSortCommand(processor, data);
                            commandInvoker.executeCommand(command);
                        }
                    }

                    case EXIT -> {
                        System.out.println("Выход...");
                        scanner.close();
                        return;
                    }

                    case UNDO -> commandInvoker.undoLastCommand();

                    default -> System.out.println("Не верный выбор!");
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
