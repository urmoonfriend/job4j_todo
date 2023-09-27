package kz.job4j.toDo.controller;

import kz.job4j.toDo.model.entity.Task;
import kz.job4j.toDo.model.request.TaskRequest;
import kz.job4j.toDo.service.TaskService;
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
    public String create(@ModelAttribute TaskRequest task, Model model) {
        try {
            taskService.create(task);
            return REDIRECT_TASKS;
        } catch (Exception exception) {
            model.addAttribute(MESSAGE_ATTRIBUTE, exception.getMessage());
            return NOT_FOUND_PAGE;
        }
    }

    @GetMapping("/done/{id}")
    public String getDone(Model model, @PathVariable("id") Integer id) {
        var taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            return NOT_FOUND_PAGE;
        }
        Task taskToUpdate = taskOpt.get().setDone(true);
        Task updatedTask = taskService.update(taskToUpdate);
        model.addAttribute("task", updatedTask);
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
    public String update(@ModelAttribute Task task, Model model) {
        try {
            var taskOpt = taskService.findById(task.getId());
            if (taskOpt.isEmpty()) {
                model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
                return NOT_FOUND_PAGE;
            }
            var updatedTask = taskService.update(
                    new Task()
                            .setId(taskOpt.get().getId())
                            .setDescription(task.getDescription())
                            .setDone(task.getDone())
                            .setCreated(taskOpt.get().getCreated())
            );
            model.addAttribute("task", updatedTask);
            return TASKS_ONE;
        } catch (Exception exception) {
            model.addAttribute(MESSAGE_ATTRIBUTE, exception.getMessage());
            return NOT_FOUND_PAGE;
        }
    }
}
