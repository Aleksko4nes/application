package application.processor.input;

import application.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomProcess {

    public static List<Person> generateRandomPersons(int size) {

        Random randomGenerator = new Random();
        List<Person> personList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int randomSex = randomGenerator.nextInt(2);
            if (randomSex == 0) {
                int randomIntName = randomGenerator.nextInt(RandomData.womenNamesArray.length);
                String randomName = RandomData.womenNamesArray[randomIntName];
                int randomIntLastName = randomGenerator.nextInt(RandomData.womenLastNamesArray.length);
                String randomLastName = RandomData.womenLastNamesArray[randomIntLastName];
                int randomAge = randomGenerator.nextInt(149) + 1;
                personList.add(new Person.Builder()
                        .name(randomName)
                        .lastName(randomLastName)
                        .age(randomAge)
                        .build());

            } else if (randomSex == 1) {
                int randomIntName = randomGenerator.nextInt(RandomData.maleNamesArray.length);
                String randomName = RandomData.maleNamesArray[randomIntName];
                int randomIntLastName = randomGenerator.nextInt(RandomData.maleLastNamesArray.length);
                String randomLastName = RandomData.maleLastNamesArray[randomIntLastName];
                int randomAge = randomGenerator.nextInt(149) + 1;
                personList.add(new Person.Builder()
                        .name(randomName)
                        .lastName(randomLastName)
                        .age(randomAge)
                        .build());
            }
        }
        return personList;
    }
}