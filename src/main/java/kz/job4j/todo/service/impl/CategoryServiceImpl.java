package kz.job4j.todo.service.impl;

import kz.job4j.todo.model.entity.Category;
import kz.job4j.todo.repository.CategoryRepository;
import kz.job4j.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAllByIds(List<Integer> categories) {
        List<Category> categoriesToReturn = new ArrayList<>();
        categories.forEach(
                id -> {
                    Optional<Category> categoryOpt = categoryRepository.findById(id);
                    categoryOpt.ifPresent(categoriesToReturn::add);
                }
        );
        return categoriesToReturn;
    }
}
