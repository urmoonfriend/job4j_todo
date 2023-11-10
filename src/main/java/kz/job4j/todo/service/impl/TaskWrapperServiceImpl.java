package kz.job4j.todo.service.impl;

import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.service.TaskWrapperService;
import kz.job4j.todo.service.TimeZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskWrapperServiceImpl implements TaskWrapperService {
    private final TimeZoneService timeZoneService;

    public List<Task> getConverted(List<Task> taskList, String toTimeZoneId) {
        List<Task> listToReturn = new ArrayList<>();
        taskList.forEach(
                task -> {
                    if (task.getUser() != null && task.getUser().getUserZoneId() != null) {
                        task.setCreated(
                                timeZoneService.convert(
                                        Optional.of(task.getUser().getUserZoneId()),
                                        Optional.of(toTimeZoneId),
                                        task.getCreated())
                        );
                        listToReturn.add(task);
                    }
                }
        );
        return listToReturn;
    }

    public Optional<Task> getConverted(Optional<Task> taskOpt, String toTimeZoneId) {
        Optional<Task> taskToReturn = Optional.empty();
        if (taskOpt.isPresent()) {
            var task = taskOpt.get();
            if (task.getUser() != null && task.getUser().getUserZoneId() != null) {
                task.setCreated(
                        timeZoneService.convert(
                                Optional.of(task.getUser().getUserZoneId()),
                                Optional.of(toTimeZoneId),
                                task.getCreated())
                );
                taskToReturn = Optional.of(task);
            }
        }
        return taskToReturn;
    }

}
