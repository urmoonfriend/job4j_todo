package kz.job4j.todo.service.impl;

import kz.job4j.todo.mapper.TaskMapper;
import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.model.entity.Category;
import kz.job4j.todo.model.entity.Priority;
import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.repository.TaskRepository;
import kz.job4j.todo.service.CategoryService;
import kz.job4j.todo.service.PriorityService;
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
    private final PriorityService priorityService;

    private final CategoryService categoryService;

    @Override
    public Optional<Task> create(TaskDto task) {
        Optional<Priority> priorityOpt = priorityService.findById(task.getPriority().getId());
        priorityOpt.ifPresent(task::setPriority);
        List<Category> categories = categoryService.findAllByIds(task.getCategoryIds());
        task.setCategories(categories);
        return taskRepository.create(taskMapper.getEntityFromDto(task));
    }

    @Override
    public Optional<Task> update(TaskDto taskDto) {
        Optional<Task> taskToUpdate = findById(taskDto.getId());
        if (taskToUpdate.isPresent()) {
            Optional<Priority> priorityOpt = priorityService.findById(taskDto.getPriority().getId());
            priorityOpt.ifPresent(taskDto::setPriority);
            List<Category> categories = categoryService.findAllByIds(taskDto.getCategoryIds());
            taskDto.setCategories(categories);
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
            var task = taskToUpdate.get().setDone(true);
            taskToUpdate = taskRepository.update(task);
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
