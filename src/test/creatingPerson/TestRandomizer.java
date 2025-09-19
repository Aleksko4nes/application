package test.creatingPerson;

import application.entity.Person;
import java.util.Arrays;
import java.util.List;

import static application.processor.input.RandomProcess.generateRandomPersons;

public class TestRandomizer {
    public static void main(String[] args) {

        List<Person> personArray;
        personArray = generateRandomPersons(200);
        System.out.println(Arrays.toString(personArray.stream().toArray()));
    }
}


