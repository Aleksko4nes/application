package application.processor.input;

import application.entity.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManualJsonReader {
    public static Person parsePerson(String personData) {
        try {
            Person.Builder builder = new Person.Builder();
            String[] fields = personData.split(",");

            for (String field : fields) {
                String[] keyValue = field.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");

                    switch (key) {
                        case "name" -> builder.name(value);
                        case "lastName" -> builder.lastName(value);
                        case "age" -> builder.age(Integer.parseInt(value));
                    }
                }
            }

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }
}
