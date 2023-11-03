package kz.job4j.todo.repository.impl;

import kz.job4j.todo.model.entity.Category;
import kz.job4j.todo.repository.CategoryRepository;
import kz.job4j.todo.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HibernateCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return crudRepository.optional("from Category where id = :cId", Map.of("cId", id));
    }
}
