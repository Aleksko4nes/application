package application.manager.sorting;

import application.entity.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortingByName<T extends Person> implements SortingStrategy<T>{
    @Override
    public List<T> sort(List<T> collection) {
        return collection.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(T::getName))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
