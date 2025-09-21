package application.processor.input;

import application.entity.Person;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomProcess {
    public static List<Person> generateRandomPersons(int size) {
        Random randomGenerator = new Random();
        List<Person> personList = IntStream.range(0, size)
                .mapToObj(i -> {
                    int randomSex = randomGenerator.nextInt(2);
                    String randomName, randomLastName;
                    int randomAge = randomGenerator.nextInt(149) + 1;
                    if (randomSex == 0) {
                        int randomIntName = randomGenerator.nextInt(RandomData.womenNamesArray.length);
                        randomName = RandomData.womenNamesArray[randomIntName];
                        int randomIntLastName = randomGenerator.nextInt(RandomData.womenLastNamesArray.length);
                        randomLastName = RandomData.womenLastNamesArray[randomIntLastName];
                    } else {
                        int randomIntName = randomGenerator.nextInt(RandomData.maleNamesArray.length);
                        randomName = RandomData.maleNamesArray[randomIntName];
                        int randomIntLastName = randomGenerator.nextInt(RandomData.maleLastNamesArray.length);
                        randomLastName = RandomData.maleLastNamesArray[randomIntLastName];
                    }
                    return new Person.Builder()
                            .name(randomName)
                            .lastName(randomLastName)
                            .age(randomAge)
                            .build();
                })
                .collect(Collectors.toList());
        return personList;
    }
}
