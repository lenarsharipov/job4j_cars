package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    private final CrudRepository crudRepository;

    /**
     * Save User in DB.
     * @param user user.
     * @return User with specified ID.
     */
    public Optional<User> create(User user) {
        Optional<User> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            result = Optional.of(user);
        } catch (Exception exception) {
            LOG.error("Unable to save a specified User", exception);
        }
        return result;
    }

    /**
     * Update User in DB.
     * @param user User.
     */
    public boolean update(User user) {
        var result = true;
        try {
            crudRepository.run(session -> session.merge(user));
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to update a specified User", exception);
        }
        return result;
    }

    /**
     * Delete User by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = true;
        try {
            crudRepository.run(
                    "DELETE FROM User WHERE id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to delete User with a specified ID", exception);
        }
        return result;
    }

    /**
     * List existed Users ordered by ID ASC.
     * @return list of users.
     */
    public List<User> findAllOrderById() {
        List<User> result = Collections.emptyList();
        try {
            result = crudRepository.query("FROM User ORDER BY id ASC", User.class);
        } catch (Exception exception) {
            LOG.error("Unable to list Users", exception);
        }
        return result;
    }

    /**
     * Find User by specified ID.
     * @return User.
     */
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    "FROM User WHERE id = :fId", User.class,
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            LOG.error("Unable to get the User specified by ID");
        }
        return result;
    }

    /**
     * List user/users found by login (LIKE %key%).
     * @param key searching key.
     * @return list of users.
     */
    public List<User> findByLikeLogin(String key) {
        List<User> result = Collections.emptyList();
        try {
            result = crudRepository.query(
                    "FROM User u WHERE u.login LIKE :fKey", User.class,
                    Map.of("fKey", "%" + key + "%")
            );
        } catch (Exception exception) {
            LOG.error("Unable to get Users specified by login", exception);
        }
        return result;
    }

    /**
     * Find User by specified login.
     * @param login User login
     * @return Optional of user.
     */
    public Optional<User> findByLogin(String login) {
        Optional<User> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    "FROM User u WHERE u.login = :fLogin", User.class,
                    Map.of("fLogin", login)
            );
        } catch (Exception exception) {
            LOG.error("Unable to get User specified by login", exception);
        }
        return result;
    }

}