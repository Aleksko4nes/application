package application.processor.input;

import java.util.List;

public interface InputStrategy<T> {
    List<T> load(String name);
}
