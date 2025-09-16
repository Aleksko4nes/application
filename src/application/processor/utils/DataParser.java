package application.processor.utils;

public interface DataParser<T> {
    T parseFromString(String data) throws IllegalArgumentException;
    T parseFromJson(String jsonData) throws IllegalArgumentException;
}
