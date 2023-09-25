package kz.job4j.toDo.service.impl;

import kz.job4j.toDo.model.entity.Task;
import kz.job4j.toDo.model.request.TaskRequest;
import kz.job4j.toDo.repository.TaskRepository;
import kz.job4j.toDo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;


    @Override
    public Task create(TaskRequest task) {
        return taskRepository.create(
                new Task()
                        .setDescription(task.getDescription())
                        .setDone(task.isDone())
        );
    }

    @Override
    public Task update(TaskRequest task) {
        return taskRepository.update(
                new Task()
                        .setId(task.getId())
                        .setDescription(task.getDescription())
                        .setDone(task.isDone())
        );
    }

    @Override
    public void delete(Integer id) {
        taskRepository.delete(id);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllNew() {
        return taskRepository.findAllNew();
    }

    @Override
    public List<Task> findAllDone() {
        return taskRepository.findAllDone();
    }
}
