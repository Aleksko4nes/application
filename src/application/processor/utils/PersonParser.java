package application.processor.utils;

import application.entity.Person;

public class PersonParser implements DataParser<Person> {

    @Override
    public Person parseFromString(String data) {
        String[] parts = data.trim().split("\\s+");
        if (parts.length != 3) {
            System.out.println("В файле находятся невалидные данные!");
        }

        return new Person.Builder()
                .name(parts[0].trim())
                .lastName(parts[1].trim())
                .age(Integer.parseInt(parts[2].trim()))
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
