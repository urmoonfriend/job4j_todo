package kz.job4j.todo.service;

import kz.job4j.todo.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLoginAndPassword(String email, String password);

    User create(User user);

    User update(User user);

    void delete(Integer id);

    Optional<User> findById(Integer id);
}
