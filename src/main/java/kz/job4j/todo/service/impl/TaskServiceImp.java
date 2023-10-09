package kz.job4j.todo.service.impl;

import kz.job4j.todo.exception.DatabaseException;
import kz.job4j.todo.exception.TaskNotFoundException;
import kz.job4j.todo.mapper.TaskMapper;
import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.repository.TaskRepository;
import kz.job4j.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task create(TaskDto task) throws DatabaseException {
        return taskRepository.create(
                taskMapper.detEntityFromDto(task)
        ).orElseThrow(() -> new DatabaseException("User not created"));
    }

    @Override
    public Task update(TaskDto task) throws DatabaseException, TaskNotFoundException {
        var taskOpt = findById(task.getId()).orElseThrow(
                () -> new TaskNotFoundException("Задача с указанным идентификатором не найдена"));
        return taskRepository.update(
                taskMapper.detEntityFromDto(task)
                        .setCreated(taskOpt.getCreated())
        ).orElseThrow(() -> new DatabaseException("User not updated"));
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
