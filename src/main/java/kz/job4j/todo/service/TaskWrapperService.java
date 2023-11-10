package kz.job4j.todo.service;

import kz.job4j.todo.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskWrapperService {
    List<Task> getConverted(List<Task> taskList, String toTimeZoneId);

    Optional<Task> getConverted(Optional<Task> taskOpt, String toTimeZoneId);
}
