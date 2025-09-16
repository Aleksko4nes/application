package application.processor.utils;

import application.entity.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonParser implements DataParser<Person> {

    @Override
    public Person parseFromString(String data) throws IllegalArgumentException {
        String trimmedLine = data.trim();

        // Строгое регулярное выражение для формата "Name LastName Age"
        // Разрешаем: запятые, точки, пробелы, точки с запятой как разделители
        // Но требуюем именно 3 компонента
        Pattern pattern = Pattern.compile(
                "^([a-zA-Zа-яА-ЯёЁ]+)[,;.\\s]+([a-zA-Zа-яА-ЯёЁ]+)[,;.\\s]+(\\d+)$"
        );

        Matcher matcher = pattern.matcher(trimmedLine);
        String[] parts = trimmedLine.split("[,;.\\s]+");

        if (!matcher.find()) {
            throw new IllegalArgumentException("Неверный формат данных. Ожидается: Имя Фамилия Возраст");
        }

        String name = matcher.group(1);
        String lastName = matcher.group(2);
        //int age = Integer.parseInt(matcher.group(3));
        String ageStr = parts[2].trim();

        // Проверяем, что имя и фамилия состоят только из букв
        if (!name.matches("[a-zA-Zа-яА-ЯёЁ]{2,}") || !lastName.matches("[a-zA-Zа-яА-ЯёЁ]{2,}")) {
            throw new IllegalArgumentException("Имя и фамилия должны содержать только буквы (минимум 2)");
        }

        // Проверяем, что возраст - число
        if (!ageStr.matches("\\d+")) {
            throw new IllegalArgumentException("Возраст должен быть числом");
        }

        int age = Integer.parseInt(ageStr);

        return new Person.Builder()
                .name(name)
                .lastName(lastName)
                .age(age)
                .build();
    }

    @Override
    public Person parseFromJson(String jsonData) {
        Person.Builder builder = new Person.Builder();
        String[] fields = jsonData.split(",");

        try {
            for (String field : fields) {
                String[] keyValue = field.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replace("\"", "").replace("{", "").replace("}", "");
                    String value = keyValue[1].trim().replace("\"", "");

                    switch (key) {
                        case "name" -> builder.name(value);
                        case "lastName" -> builder.lastName(value);
                        case "age" -> builder.age(Integer.parseInt(value));
                        default -> System.out.println("Неизвестное поле" + key);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("В файле обнаружены невалидные данные!" + e.getMessage());
        }

        return builder.build();
    }
}
