package kz.job4j.toDo.repository.impl;

import kz.job4j.toDo.model.entity.Task;
import kz.job4j.toDo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HibernateTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    @Override
    public Task create(Task task) {
        Session session = sf.openSession();
        Task taskToCreate = null;
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            taskToCreate = task;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return taskToCreate;
    }

    @Override
    public Task update(Task task) {
        Session session = sf.openSession();
        Task taskToUpdate = null;
        try {
            session.beginTransaction();
            session.createQuery("UPDATE Task SET description = :tDescription, done = :tDone WHERE id = :tId")
                    .setParameter("tDescription", task.getDescription())
                    .setParameter("tDone", task.getDone())
                    .setParameter("tId", task.getId())
                    .executeUpdate();
            taskToUpdate = task;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return taskToUpdate;
    }

    @Override
    public void delete(Integer id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :tId")
                    .setParameter("tId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Session session = sf.openSession();
        return session.createQuery("from Task as i where i.id = :tId")
                .setParameter("tId", id).uniqueResultOptional();
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from Task", Task.class).list();
        session.getTransaction().commit();
        log.info("tasks size: [{}]", result.size());
        return result;
    }
}
