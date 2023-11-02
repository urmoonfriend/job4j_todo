package kz.job4j.todo.service;

import kz.job4j.todo.model.entity.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityService {
    List<Priority> findAll();

    Optional<Priority> findById(Integer id);
}
