package kz.job4j.todo.service;

import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> create(TaskDto task);

    Optional<Task> update(TaskDto task);

    Optional<Task> completeTask(Integer id);

    void delete(Integer id);

    Optional<Task> findById(Integer id);

    List<Task> findAll();

    List<Task> findAllNew();

    List<Task> findAllDone();
}
