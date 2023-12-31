package kz.job4j.todo.repository;

import kz.job4j.todo.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> create(Task task);

    Optional<Task> update(Task task);

    void delete(Integer id);

    Optional<Task> findById(Integer id);

    List<Task> findAll();

    List<Task> findAllNew();

    List<Task> findAllDone();
}
