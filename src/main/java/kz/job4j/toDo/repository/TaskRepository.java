package kz.job4j.toDo.repository;

import kz.job4j.toDo.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task create(Task task);

    Task update(Task task);

    void delete(Integer id);

    Optional<Task> findById(Integer id);

    List<Task> findAll();
}
