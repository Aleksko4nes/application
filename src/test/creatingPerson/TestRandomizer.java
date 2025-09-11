package RandomInputTest;

import application.entity.Person;
import java.util.Arrays;

import static application.manager.input_processor.RandomInput.generateRandomPersons;

public class TestRandomizer {
    public static void main(String[] args) {

        Person[] personArray = new Person[3];
        personArray = generateRandomPersons(200);
        System.out.println(Arrays.toString(Arrays.stream(personArray).toArray()));
    }
}


