package application.processor.input;

import application.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class ManualInput<T extends Person> implements InputStrategy<T>{
    @Override
    public List<T> load(String value) {
        List<T> people = new ArrayList<>();

        if (value == null || value.trim().isEmpty()){
            System.out.println("Пустая строка!");
            return people;
        }

        String[] personalData = value.trim().split("\\s+");

        if (personalData.length != 3) {
            System.out.println("Ошибка ввода: Необходимо ввести \"Имя, Фамилию и Возраст\"");
            return people;
        }

        if (personalData[2].matches("\\d+")) {
            int age = Integer.parseInt(personalData[2]);

            @SuppressWarnings("unchecked")
            T person = (T) new Person.Builder()
                    .name(personalData[0])
                    .lastName(personalData[1])
                    .age(age)
                    .build();

            people.add(person);
        } else {
            System.out.println("Возраст должен быть числом!");
        }

        return people;
    }
}
