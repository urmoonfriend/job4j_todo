package kz.job4j.todo.controller;

import kz.job4j.todo.exception.DatabaseException;
import kz.job4j.todo.exception.TaskNotFoundException;
import kz.job4j.todo.mapper.TaskMapper;
import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private static final String NOT_FOUND_MESSAGE = "Задача с указанным идентификатором не найдена";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String REDIRECT_TASKS = "redirect:/tasks";
    private static final String TASKS_LIST = "tasks/list";
    private static final String TASKS_ONE = "tasks/one";
    private static final String TASKS_ATTRIBUTE = "tasks";
    private static final String NOT_FOUND_PAGE = "errors/404";

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute(TASKS_ATTRIBUTE, taskService.findAll());
        return TASKS_LIST;
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(TASKS_ATTRIBUTE, taskService.findAllNew());
        return TASKS_LIST;
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute(TASKS_ATTRIBUTE, taskService.findAllDone());
        return TASKS_LIST;
    }

    @GetMapping("/{id}")
    public String getTask(Model model, @PathVariable Integer id) {
        log.info("getTask method request: [{}]", id);
        Optional<Task> taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute("task", taskOpt.get());
        return TASKS_ONE;
    }

    @GetMapping("/create")
    public String createTask(Model model) {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TaskDto task, Model model) {
        try {
            taskService.create(task);
            return REDIRECT_TASKS;
        } catch (Exception | DatabaseException e) {
            log.error(e.getMessage());
            model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
            return NOT_FOUND_PAGE;
        }
    }

    @GetMapping("/done/{id}")
    public String getDone(Model model, @PathVariable("id") Integer id) {
        var taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            return NOT_FOUND_PAGE;
        }
        try {
            Task taskToUpdate = taskOpt.get().setDone(true);
            Task updatedTask = taskService.update(taskMapper.getModelFromEntity(taskToUpdate));
            model.addAttribute("task", updatedTask);
        } catch (Exception | DatabaseException | TaskNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
            return NOT_FOUND_PAGE;
        }
        return TASKS_ONE;
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") Integer id) {
        var taskToDelete = taskService.findById(id);
        if (taskToDelete.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        taskService.delete(id);
        return REDIRECT_TASKS;
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Integer id) {
        var taskToUpdate = taskService.findById(id);
        if (taskToUpdate.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute("task", taskToUpdate.get());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TaskDto task, Model model) {
        try {
            var updatedTask = taskService.update(task);
            model.addAttribute("task", updatedTask);
            return TASKS_ONE;
        } catch (Exception | DatabaseException | TaskNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
            return NOT_FOUND_PAGE;
        }
    }
}
