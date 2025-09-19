package application.entity;

import java.util.Objects;

public class Person implements Comparable<Person> {
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

    @Override
    public int compareTo(Person o) {
        int cmp = this.name.compareTo(o.name);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(this.age, o.age);
        if(cmp != 0) return cmp;
        return this.lastName.compareTo(o.lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, age);
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

 /*       public boolean doQualityCheck() {
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
            */
                public Person build() {
        return new Person(this);
    }
    }
}
