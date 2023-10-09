package kz.job4j.todo.service;

import kz.job4j.todo.exception.DatabaseException;
import kz.job4j.todo.exception.TaskNotFoundException;
import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.model.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task create(TaskDto task) throws DatabaseException;

    Task update(TaskDto task) throws DatabaseException, TaskNotFoundException;

    void delete(Integer id);

    Optional<Task> findById(Integer id);

    List<Task> findAll();

    List<Task> findAllNew();

    List<Task> findAllDone();
}
