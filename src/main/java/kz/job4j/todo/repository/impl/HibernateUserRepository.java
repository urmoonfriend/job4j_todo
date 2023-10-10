package kz.job4j.todo.repository.impl;

import kz.job4j.todo.model.entity.User;
import kz.job4j.todo.repository.CrudRepository;
import kz.job4j.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> create(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.of(user);
    }

    @Override
    public User update(User user) {
        crudRepository.run(session -> session.merge(user));
        return user;
    }

    @Override
    public void delete(Integer id) {
        crudRepository.run("DELETE User WHERE id = :uId", Map.of("uId", id));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return crudRepository.optional("from User where id = :uId", Map.of("uId", id));
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional("from User where login = :uLogin and password = :uPassword",
                Map.of("uLogin", login, "uPassword", password));
    }

}
