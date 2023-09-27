package kz.job4j.toDo.service;

import kz.job4j.toDo.model.entity.Task;
import kz.job4j.toDo.model.request.TaskRequest;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task create(TaskRequest task);

    Task update(Task task);

    void delete(Integer id);

    Optional<Task> findById(Integer id);

    List<Task> findAll();

    List<Task> findAllNew();

    List<Task> findAllDone();
}
