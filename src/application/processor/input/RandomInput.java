package application.manager.input_processor;

import application.entity.Person;
import java.util.List;
import java.util.Random;

public class RandomInput<T extends Person> implements InputStrategy<T> {

        @Override
        public List<T> load (String s){
            return null;//TODO: рандомное заполнение коллекции
        }

        public static Person[] generateRandomPersons (int size) {
            // от size зависит количество создаваемых персон

            Random randomGenerator = new Random();
            Person[] persons = new Person[size];
            // UPD: Списки с фамилиями и именами были перенесены в отдельный файл RandomData
            for (int i = 0; i < size; i++) {
                int randomSex = randomGenerator.nextInt(2);
                // от 0 до 1 (2 не включается), если 0, то создаём женщину со случайным именем и фамилией
                // из womenNamesArray и womenLastNamesArray с возрастом от 1 до 149 лет,
                // если выпадает число 1, то мужчину с соответствующими ему полями
                if (randomSex == 0) {
                    // выпало число 0, создаём женщину
                    int randomIntName = randomGenerator.nextInt(RandomData.womenNamesArray.length);
                    // UPD: рандомное число из промежутка длины массива с женскими именами womenNamesArray
                    String randomName = RandomData.womenNamesArray[randomIntName];
                    // присвоение женского имени из массива womenNamesArray в зависимости от randomIntName
                    int randomIntLastName = randomGenerator.nextInt(RandomData.womenLastNamesArray.length);
                    // UPD: рандомное число из промежутка длины массива с женскими фамилиями womenLastNamesArray
                    String randomLastName = RandomData.womenLastNamesArray[randomIntLastName];
                    // присвоение женской фамилии из массива womenLastNamesArray в зависимости от randomIntLastName
                    int randomAge = randomGenerator.nextInt(149) + 1;
                    // присвоение рандомного возраста от 1 до 149 лет
                    persons[i] = new Person.Builder()
                            .name(randomName)
                            .lastName(randomLastName)
                            .age(randomAge)
                            .build();

                } else if (randomSex == 1) {
                    // выпало число 1, то по аналогии с женщиной создаем мужчину, используя
                    // вместо womenNamesArray - maleNamesArray, а вместо womenLastNamesArray - maleLastNamesArray
                    int randomIntName = randomGenerator.nextInt(RandomData.maleNamesArray.length);
                    String randomName = RandomData.maleNamesArray[randomIntName];
                    int randomIntLastName = randomGenerator.nextInt(RandomData.maleLastNamesArray.length);
                    String randomLastName = RandomData.maleLastNamesArray[randomIntLastName];
                    int randomAge = randomGenerator.nextInt(149) + 1;
                        persons[i] = new Person.Builder()
                                .name(randomName)
                                .lastName(randomLastName)
                                .age(randomAge)
                                .build();
                }
            }
            return persons;
        }
}