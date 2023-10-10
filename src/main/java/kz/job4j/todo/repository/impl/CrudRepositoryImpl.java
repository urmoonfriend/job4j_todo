package kz.job4j.todo.repository.impl;

import kz.job4j.todo.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CrudRepositoryImpl implements CrudRepository {
    private final SessionFactory sessionFactory;

    public void run(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    public void run(String query, Map<String, Object> params) {
        Consumer<Session> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                sq.setParameter(param.getKey(), param.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    public <T> List<T> query(String query, Class<T> root, Map<String, Object> params) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, root);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                sq.setParameter(param.getKey(), param.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> root) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, root);
            return sq.list();
        };
        return tx(command);
    }

    public <T> Optional<T> optional(String query, Map<String, Object> params) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                sq.setParameter(param.getKey(), param.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        Session session = sessionFactory.openSession();
        Optional<Transaction> transaction = Optional.empty();
        try {
            transaction = Optional.of(session.beginTransaction());
            T result = command.apply(session);
            transaction.get().commit();
            return result;
        } catch (Exception e) {
            transaction.ifPresent(EntityTransaction::rollback);
            throw e;
        } finally {
            session.close();
        }
    }
}
