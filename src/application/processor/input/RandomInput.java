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
            // Ниже списки мужских/женских имён и фамилий
            //-------------------------------------------------------------------------------------------------------------------------------------------------------------
            final String[] maleNamesArray = {"Ivan", "Artem", "Aleksander", "Silvester",  "Artur", "Maxim", "Grigoriy", "Efim", "John", "Alexey",
                    "Dominic", "Vasiliy", "Oleg", "Akakiy", "Sergey", "Nikolya", "Tihon", "Ilya", "Ildar", "Svyatoslav",
                    "Elisey", "Anatoliy", "Ibragym", "Takumi", "Charlie", "Shaman", "Kesha", "Konstantin", "Evgeniy", "Ravshan", "Vladimir"};
            final String[] womenNamesArray = {"Olga", "Alina", "Svetlana", "Anastasia", "Anjelika", "Eva", "Madonna", "Viktoria", "Elena", "Natalya",
                    "Afdotya", "Vasilisa", "Alena", "Angelina", "Alla", "Oksana", "Leti", "Nazuki", "Karina", "Yana",
                    "Milena", "Valeriya", "Irina", "Darya", "Anna", "Polina", "Ekaterina", "Alisa", "Maria", "Galina", "Monika", "Aleksandra"};
            final String[] maleLastNamesArray = {"Ivanov", "Kolesnikov", "Stalone", "Swarcneger", "Kochetkov", "Shumilov", "Serov", "Fujivara", "Toretto", "Polezhaikin",
                    "Ahmedov", "Abubakirov", "Kvasov", "Korzhukov", "Negreev", "Zolo", "Pushkin", "Tatarkin", "Kramarchuk", "Tramp",
                    "Belozerov", "Bataikin", "Tihonov", "Danilec", "Rahmatulin", "Baturin", "Domrachev", "Suharev", "Grinkin", "Soloviev", "Mazurov"};
            final String[] womenLastNamesArray = {"Ivanova", "Kolesnikova", "Stalone", "Swarcneger", "Kochetkova", "Shumilova", "Serova", "Fujivara", "Toretto", "Polezhaikina",
                    "Ahmedova", "Abubakirova", "Kvasova", "Korzhukova", "Negreeva", "Zolo", "Pushkina", "Tatarkina", "Kramarchuk", "Tramp",
                    "Belozerova", "Bataikina", "Tihonova", "Danilec", "Rahmatulina", "Baturina", "Domracheva", "Suhareva", "Grinkina", "Solovieva", "Mazurov"};
            int arrayLength = 31;

            for (int i = 0; i < size; i++) {
                int randomSex = randomGenerator.nextInt(2);
                // от 0 до 1 (2 не включается), если 0, то создаём женщину со случайным именем и фамилией
                // из womenNamesArray и womenLastNamesArray с возрастом от 1 до 149 лет,
                // если выпадает число 1, то мужчину с соответствующими ему полями
                if (randomSex == 0) {
                    // выпало число 0, создаём женщину
                    int randomIntName = randomGenerator.nextInt(arrayLength);
                    // рандомное число от 0 до 30
                    String randomName = womenNamesArray[randomIntName];
                    // присвоение женского имени из массива womenNamesArray в зависимости от randomIntName
                    int randomIntLastName = randomGenerator.nextInt(arrayLength);
                    // рандомное число от 0 до 30
                    String randomLastName = womenLastNamesArray[randomIntLastName];
                    // присвоение женской фамилии из массива womenLastNamesArray в зависимости от randomIntLastName
                    int randomAge = randomGenerator.nextInt(149) + 1;
                    // присвоение рандомного возраста от 1 до 149
                    persons[i] = new Person.Builder()
                            .name(randomName)
                            .lastName(randomLastName)
                            .age(randomAge)
                            .build();

                } else if (randomSex == 1) {
                    // выпало число 1, то по аналогии с женщиной создаем мужчину, используя
                    // вместо womenNamesArray - maleNamesArray, а вместо womenLastNamesArray - maleLastNamesArray
                    int randomIntName = randomGenerator.nextInt(arrayLength);
                    String randomName = maleNamesArray[randomIntName];
                    int randomIntLastName = randomGenerator.nextInt(arrayLength);
                    String randomLastName = maleLastNamesArray[randomIntLastName];
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