package application.processor.input;

import application.entity.Person;
import java.util.List;
import static application.processor.input.RandomProcess.generateRandomPersons;

public class RandomInput<T extends Person> implements InputStrategy<T> {

    @Override
    public List<T> load(String size) {
        List<T> returnAbleList = List.of();
        try {
            String temp = size.replaceAll("[^0-9]", "");
            int sizeInt = 0 + Integer.parseInt(temp);
            if (sizeInt <= 0 || temp == null || temp.isEmpty()) {
                System.out.println("Введённое значение не корректно!");
            } else {
                returnAbleList = (List<T>) generateRandomPersons(sizeInt);
            }
        } catch (Exception e) {
            System.out.println("Данные введены некорректно");
        }
         return returnAbleList;
    }
}