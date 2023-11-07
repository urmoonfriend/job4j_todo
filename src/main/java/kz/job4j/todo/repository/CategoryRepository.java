package kz.job4j.todo.repository;

import kz.job4j.todo.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();

    Optional<Category> findById(Integer id);

    List<Category> findAllIn(List<Integer> categoryIds);
}
