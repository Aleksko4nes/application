package application.manager.input_processor;

import java.util.List;

public interface InputStrategy<T> {
    List<T> load(String name);
}
