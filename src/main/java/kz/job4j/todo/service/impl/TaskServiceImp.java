package kz.job4j.todo.service.impl;

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
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Optional<Task> create(TaskDto task) {
        return taskRepository.create(taskMapper.getEntityFromDto(task));
    }

    @Override
    public Optional<Task> update(TaskDto taskDto) {
        AtomicReference<Optional<Task>> taskToUpdate = new AtomicReference<>(Optional.empty());
        findById(taskDto.getId())
                .ifPresent(
                        taskOpt -> taskToUpdate.set(taskRepository.update(
                                taskMapper.getEntityFromDto(taskDto)
                                        .setCreated(taskOpt.getCreated())
                        ))
                );
        return taskToUpdate.get();
    }

    @Override
    public Optional<Task> completeTask(Integer id) {
        AtomicReference<Optional<Task>> taskToUpdate = new AtomicReference<>(Optional.empty());
        findById(id)
                .ifPresent(
                        taskOpt -> taskToUpdate.set(taskRepository.update(
                                taskMapper.getEntityFromDto(
                                        new TaskDto()
                                                .setId(id)
                                                .setTitle(taskOpt.getTitle())
                                                .setDescription(taskOpt.getDescription())
                                                .setDone(true)
                                        )
                                        .setCreated(taskOpt.getCreated())
                        ))
                );
        return taskToUpdate.get();
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
