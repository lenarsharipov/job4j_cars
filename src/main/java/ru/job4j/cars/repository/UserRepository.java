package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;
import ru.job4j.cars.util.Key;
import ru.job4j.cars.util.Message;
import ru.job4j.cars.util.UserQuery;

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
            crudRepository.run(session -> session.save(user));
            result = Optional.of(user);
        } catch (Exception exception) {
            LOG.error(Message.USER_NOT_SAVED, exception);
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
            crudRepository.run(session -> session.update(user));
        } catch (Exception exception) {
            result = false;
            LOG.error(Message.USER_NOT_UPDATED, exception);
        }
        return result;
    }

    /**
     * Delete User by ID.
     * @param login Login.
     */
    public boolean delete(String login) {
        var result = false;
        try {
            result = crudRepository.isExecuted(UserQuery.DELETE, Map.of(Key.LOGIN, login));
        } catch (Exception exception) {
            LOG.error(Message.USER_NOT_DELETED, exception);
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
            result = crudRepository.query(UserQuery.FIND_ALL, User.class);
        } catch (Exception exception) {
            LOG.error(Message.USERS_NOT_LISTED, exception);
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
                    UserQuery.FIND_BY_ID, User.class, Map.of(Key.ID, id));
        } catch (Exception exception) {
            LOG.error(Message.USER_NOT_FOUND_BY_ID, exception);
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
                    UserQuery.FIND_BY_LIKE_LOGIN, User.class,
                    Map.of(Key.KEY, "%" + key + "%")
            );
        } catch (Exception exception) {
            LOG.error(Message.USERS_NOT_FOUND_BY_LIKE_LOGIN, exception);
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
                    UserQuery.FIND_BY_LOGIN, User.class,
                    Map.of(Key.LOGIN, login)
            );
        } catch (Exception exception) {
            LOG.error(Message.USER_NOT_FOUND_BY_LOGIN, exception);
        }
        return result;
    }

}