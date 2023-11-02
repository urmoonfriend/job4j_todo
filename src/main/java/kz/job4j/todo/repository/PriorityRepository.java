package kz.job4j.todo.repository;

import kz.job4j.todo.model.entity.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityRepository {
    List<Priority> findAll();

    Optional<Priority> findById(Integer id);
}
