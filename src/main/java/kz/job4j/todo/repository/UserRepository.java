package kz.job4j.todo.repository;

import kz.job4j.todo.model.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByLoginAndPassword(String email, String password);

    Optional<User> create(User user);

    User update(User user);

    void delete(Integer id);

    Optional<User> findById(Integer id);
}
