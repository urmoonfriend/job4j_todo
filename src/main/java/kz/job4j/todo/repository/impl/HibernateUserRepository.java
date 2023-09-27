package kz.job4j.todo.repository.impl;

import kz.job4j.todo.model.entity.Task;
import kz.job4j.todo.model.entity.User;
import kz.job4j.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HibernateUserRepository implements UserRepository {
    private final SessionFactory sf;

    @Override
    public User create(User user) {
        log.info("create user method request: [{}]", user);
        Session session = sf.openSession();
        User userToCreate = null;
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            userToCreate = user;
        } catch (Exception e) {
            log.error("create user method error: [{}]", e.getMessage());
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        log.info("create user method response: [{}]", userToCreate);
        return userToCreate;
    }

    @Override
    public User update(User user) {
        Session session = sf.openSession();
        User userToUpdate = null;
        try {
            session.beginTransaction();
            session.createQuery("UPDATE User SET name = :uName, login = :uLogin, password = :uPassword WHERE id = :uId")
                    .setParameter("uName", user.getName())
                    .setParameter("uLogin", user.getLogin())
                    .setParameter("uPassword", user.getPassword())
                    .setParameter("uId", user.getId())
                    .executeUpdate();
            userToUpdate = user;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userToUpdate;
    }

    @Override
    public void delete(Integer id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :uId")
                    .setParameter("uId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        Session session = sf.openSession();
        Optional<User> userOpt = Optional.empty();
        try {
            session.beginTransaction();
            userOpt = session.createQuery("from User as i where i.id = :uId")
                    .setParameter("uId", id).uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOpt;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> userOpt = Optional.empty();
        try {
            session.beginTransaction();
            userOpt = session.createQuery("from User as i where i.login = :uLogin and i.password = :uPassword")
                    .setParameter("uLogin", login)
                    .setParameter("uPassword", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userOpt;
    }

}
