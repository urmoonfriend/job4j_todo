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
        Optional<Task> taskToUpdate = findById(taskDto.getId());
        if (taskToUpdate.isPresent()) {
            taskToUpdate = taskRepository.update(
                    taskMapper.getEntityFromDto(taskDto)
                            .setCreated(taskToUpdate.get().getCreated())
            );
        }
        return taskToUpdate;
    }

    @Override
    public Optional<Task> completeTask(Integer id) {
        Optional<Task> taskToUpdate = findById(id);
        if (taskToUpdate.isPresent()) {
            taskToUpdate = taskRepository.update(
                    taskMapper.getEntityFromDto(
                                    new TaskDto()
                                            .setId(id)
                                            .setTitle(taskToUpdate.get().getTitle())
                                            .setDescription(taskToUpdate.get().getDescription())
                                            .setDone(true)
                            )
                            .setCreated(taskToUpdate.get().getCreated())
            );
        }
        return taskToUpdate;
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
