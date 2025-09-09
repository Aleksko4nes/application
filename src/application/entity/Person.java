package application.entity;

public class Person {
    private final String name;
    private final String lastName;
    private final int age;

    public Person(Builder builder) {
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person { " + '\n' +
                "\"name\" : " + name + '\n' +
                "\"lastName\" : " + lastName  + '\n' +
                "\"age\" : " + age + '\n' +
                '}';
    }

    public static class Builder {
        private String name;
        private String lastName;
        private int age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public boolean doQualityCheck() {
            return (name != null && !name.trim().isEmpty())
                    && (lastName != null && !lastName.trim().isEmpty())
                    && (age > 0 && age < 151);
        }

        public Person build() {
            if (doQualityCheck()) {
                return new Person(this);
            } else {
                System.out.println("Поле не должно быть пустым." +
                        "Возраст не должен превышать 150 лет");
            }
            return null;
        }
    }
}
