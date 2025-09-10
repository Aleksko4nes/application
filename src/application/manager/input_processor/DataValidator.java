package application.manager.input_processor;

import application.entity.Person;

import java.util.regex.Pattern;

// По хорошему создать отдельный Package utils и засунуть валидатор туда
public class DataValidator {
    //TODO: сделать валидацию данных с использованием регулярки
//    private static final Pattern NAME_PATTERN =
//            Pattern.compile("^[a-zA-Zа-яА-ЯёЁ\\s-]+$");

    public static boolean validatePersonData(String name, String lastName, String ageStr) {
        try {
            // Валидация имени
            if (name == null || name.trim().isEmpty())
//                    ||
//                    !NAME_PATTERN.matcher(name).matches())
            {
                return false;
            }

            if (lastName == null || lastName.trim().isEmpty())
//                    ||
//                    !NAME_PATTERN.matcher(lastName).matches())
            {
                return false;
            }

            // Валидация возраста
            int age = Integer.parseInt(ageStr);
            return age > 0 && age <= 150;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Person createValidatedPerson(String name, String lastName, String ageStr) {
        if (!validatePersonData(name, lastName, ageStr)) {
            throw new IllegalArgumentException("Invalid person data: " +
                    "name='" + name + "', lastName='" + lastName + "', age='" + ageStr + "'");
        }

        return new Person.Builder()
                .name(name.trim())
                .lastName(lastName.trim())
                .age(Integer.parseInt(ageStr))
                .build();
    }
}
