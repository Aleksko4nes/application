package test.creatingPerson;

import application.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonTest {
    public static void main(String[] args) {


        Person person = new Person.Builder()
                .name("Вася")
                .lastName("Петров")
                .age(12)
                .build();
        //System.out.println(person);

        Person person2 = new Person.Builder()
                .lastName("Петров")
                .age(12)
                .build();
        //System.out.println(person2);

        Person person3 = new Person.Builder()
                .name("Вася")
                .lastName("Петров")
                .age(161)
                .build();

        Person person4 = new Person.Builder()
                .name("Федор")
                .lastName("Васькин")
                .age(51)
                .build();
        //System.out.println(person3);

        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        persons.forEach(System.out::println);
    }

}

