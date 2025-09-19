package application.processor.searching;

import application.entity.Person;
import java.util.Comparator;

/**
 * Универсальный компаратор для Person.
 * Сравнивает только те поля, которые заданы у шаблона (не null / не 0).
 */
public class FlexiblePersonComparator implements Comparator<Person> {
    private final Person template;

    public FlexiblePersonComparator(Person template) {
        this.template = template;
    }

    @Override
    public int compare(Person p1, Person p2) {
        // сравнение по имени
        if (template.getName() != null) {
            int cmp = p1.getName().compareToIgnoreCase(p2.getName());
            if (cmp != 0) return cmp;
        }

        // сравнение по фамилии
        if (template.getLastName() != null) {
            int cmp = p1.getLastName().compareToIgnoreCase(p2.getLastName());
            if (cmp != 0) return cmp;
        }

        // сравнение по возрасту (если задан)
        if (template.getAge() != 0) {
            int cmp = Integer.compare(p1.getAge(), p2.getAge());
            if (cmp != 0) return cmp;
        }

        return 0; // считаем равными, если все проверенные поля совпадают
    }
}
