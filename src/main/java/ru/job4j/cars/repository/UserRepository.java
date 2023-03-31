package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

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
        }
    }

    /**
     * Список пользователей отсортированных по возрастанию ID.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        var session = sf.openSession();
        var query = session.createQuery(
                "FROM User ORDER BY id ASC", User.class
        );
        return query.list();
    }

    /**
     * Найти пользователя по ID.
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        var session = sf.openSession();
        var query = session.createQuery(
                "FROM User WHERE id = :fId", User.class);
        query.setParameter("fId", id);
        return Optional.ofNullable(query.uniqueResult());
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key ключ поиска.
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        var session = sf.openSession();
        var query = session.createQuery(
                "FROM User WHERE login LIKE :fKey", User.class);
        query.setParameter("fKey", "%" + key + "%");
        return query.list();
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
        return Optional.ofNullable(query.uniqueResult());
    }
}