package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        var session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            sf.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        var session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE User SET password = :fPassword WHERE id = :fId")
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param id ID.
     */
    public void delete(int id) {
        var session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE User WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователей отсортированных по возрастанию ID.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        var session = sf.openSession();
        List<User> result = Collections.emptyList();
        try {
            var hql = "FROM User ORDER BY id ASC";
            result = session.createQuery(hql, User.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Найти пользователя по ID.
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        var session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            var hql = "FROM User WHERE id = :fId";
            result = session.createQuery(hql, User.class)
                     .setParameter("fId", id)
                     .uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key ключ поиска.
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        var session = sf.openSession();
        List<User> result = Collections.emptyList();
        try {
            var hql = "FROM User WHERE login LIKE :fKey";
            result = session.createQuery(hql, User.class)
                    .setParameter("fKey", "%" + key + "%")
                    .list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Найти пользователя по login.
     * @param login логин
     * @return Optional of user.
     */
    public Optional<User> findByLogin(String login) {
        var session = sf.openSession();
        var query = session.createQuery(
                "FROM User WHERE login = :fLogin", User.class);
        query.setParameter("fLogin", login);
        return query.uniqueResultOptional();
    }
}