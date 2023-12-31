package kz.job4j.todo.repository.impl;

import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.repository.CrudRepository;
import kz.job4j.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HibernateTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> create(Task task) {
        crudRepository.run(session -> session.persist(task));
        return Optional.of(task);
    }

    @Override
    public Optional<Task> update(Task task) {
        crudRepository.run(session -> session.merge(task));
        return Optional.of(task);
    }

    @Override
    public void delete(Integer id) {
        crudRepository.run("delete from Task where id = :tId ",
                Map.of("tId", id));
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return crudRepository.optional("SELECT DISTINCT f from Task f JOIN FETCH f.priority "
                        + "JOIN FETCH f.categories where f.id = :tId ",
                Map.of("tId", id));
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.query("SELECT DISTINCT f from Task f JOIN FETCH f.priority "
                + "JOIN FETCH f.categories", Task.class);
    }

    @Override
    public List<Task> findAllNew() {
        LocalDateTime newTime = LocalDateTime.now().minusHours(12);
        return crudRepository.query("SELECT DISTINCT i from Task as i JOIN FETCH i.priority "
                        + "JOIN FETCH i.categories "
                        + "where i.created >= :newTime order by created desc",
                Task.class, Map.of("newTime", newTime));
    }

    @Override
    public List<Task> findAllDone() {
        return crudRepository.query("SELECT DISTINCT i from Task as i JOIN FETCH i.priority "
                + "JOIN FETCH i.categories "
                + "WHERE i.done = true", Task.class);
    }
}
