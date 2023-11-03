package kz.job4j.todo.service;

import kz.job4j.todo.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Integer id);

    List<Category> findAllByIds(List<Integer> categories);
}
