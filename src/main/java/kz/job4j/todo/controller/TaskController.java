package kz.job4j.todo.controller;

import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.model.entity.User;
import kz.job4j.todo.service.CategoryService;
import kz.job4j.todo.service.PriorityService;
import kz.job4j.todo.service.TaskService;
import kz.job4j.todo.service.TaskWrapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;
    private final TaskWrapperService taskWrapperService;
    private static final String NOT_FOUND_MESSAGE = "Задача с указанным идентификатором не найдена";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String REDIRECT_TASKS = "redirect:/tasks";
    private static final String TASKS_LIST = "tasks/list";
    private static final String TASKS_ONE = "tasks/one";
    private static final String TASKS_ATTRIBUTE = "tasks";
    private static final String NOT_FOUND_PAGE = "errors/404";

    @GetMapping
    public String getAllTasks(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute(
                TASKS_ATTRIBUTE,
                taskWrapperService.getConverted(
                        taskService.findAll(), user.getUserZoneId()));
        return TASKS_LIST;
    }

    @GetMapping("/new")
    public String getNewTasks(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute(
                TASKS_ATTRIBUTE,
                taskWrapperService.getConverted(
                taskService.findAllNew(), user.getUserZoneId()));
        return TASKS_LIST;
    }

    @GetMapping("/done")
    public String getCompletedTasks(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        model.addAttribute(
                TASKS_ATTRIBUTE,
                taskWrapperService.getConverted(
                taskService.findAllDone(),  user.getUserZoneId()));
        return TASKS_LIST;
    }

    @GetMapping("/{id}")
    public String getTask(Model model, @PathVariable Integer id, HttpSession session) {
        log.info("getTask method request: [{}]", id);
        var user = (User) session.getAttribute("user");
        Optional<Task> taskOpt = taskService.findById(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute(
                "task",
                taskWrapperService.getConverted(
                taskOpt, user.getUserZoneId()).get());
        return TASKS_ONE;
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TaskDto task, Model model, HttpSession session) {
        log.info("controller create task request: {}", task);
        task.setUser((User) session.getAttribute("user"));
        Optional<Task> taskOpt = taskService.create(task);
        if (taskOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        return REDIRECT_TASKS;
    }

    @GetMapping("/done/{id}")
    public String complete(Model model, @PathVariable("id") Integer id) {
        Optional<Task> taskOpt = taskService.completeTask(id);
        if (taskOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute("task", taskOpt.get());
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
    public String getUpdatePage(Model model, @PathVariable("id") Integer id) {
        var taskToUpdate = taskService.findById(id);
        if (taskToUpdate.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute("task", taskToUpdate.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TaskDto task, Model model, HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        Optional<Task> taskOpt = taskService.update(task);
        if (taskOpt.isEmpty()) {
            model.addAttribute(MESSAGE_ATTRIBUTE, NOT_FOUND_MESSAGE);
            return NOT_FOUND_PAGE;
        }
        model.addAttribute("task", taskOpt.get());
        return TASKS_ONE;
    }
}
