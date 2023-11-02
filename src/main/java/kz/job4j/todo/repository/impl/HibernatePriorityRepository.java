package kz.job4j.todo.repository.impl;

import kz.job4j.todo.model.entity.Priority;
import kz.job4j.todo.repository.CrudRepository;
import kz.job4j.todo.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HibernatePriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Priority> findAll() {
        return crudRepository.query("from Priority", Priority.class);
    }

    @Override
    public Optional<Priority> findById(Integer id) {
        return crudRepository.optional("from Priority where id = :pId", Map.of("pId", id));
    }
}
