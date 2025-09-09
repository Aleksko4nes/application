package test.sortTest;

import application.entity.Person;
import application.manager.sorting.Sorter;
import application.manager.sorting.SortingByAge;
import application.manager.sorting.SortingByLastName;
import application.manager.sorting.SortingByName;

import java.util.Arrays;
import java.util.List;

public class SortingTest {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person.Builder()
                        .name("Alice")
                        .lastName("McCinlig")
                        .age(32)
                        .build(),

                new Person.Builder()
                        .name("Bob")
                        .lastName("Ivanov")
                        .age(25)
                        .build(),

                new Person.Builder()
                        .name("Charlie")
                        .lastName("Semenov")
                        .age(30)
                        .build(),

                new Person.Builder()
                        .name("David")
                        .lastName("Kuzmich")
                        .age(25)
                        .build(),

                new Person.Builder()
                        .name("Eve")
                        .lastName("Vally")
                        .age(35)
                        .build(),

                new Person.Builder()
                        .name("Error")
                        .age(2)
                        .build()
        );


        Sorter<Person> ageSorter = new Sorter<>(new SortingByAge<>());
        Sorter<Person> nameSorter =new Sorter<>(new SortingByName<>());
        Sorter<Person> lastNameSorter =new Sorter<>(new SortingByLastName<>());

        System.out.println(ageSorter.sort(persons));
        System.out.println("____________________________________________________");
        System.out.println(nameSorter.sort(persons));
        System.out.println("____________________________________________________");
        System.out.println(lastNameSorter.sort(persons));

    }
}
