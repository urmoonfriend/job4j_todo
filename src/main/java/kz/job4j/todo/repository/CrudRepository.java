package kz.job4j.todo.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface CrudRepository {
    void run(Consumer<Session> command);

    void run(String query, Map<String, Object> params);

    <T> List<T> query(String query, Class<T> root, Map<String, Object> params);

    <T> List<T> query(String query, Class<T> root);

    <T> Optional<T> optional(String query, Map<String, Object> params);

    <T> T tx(Function<Session, T> command);
}
