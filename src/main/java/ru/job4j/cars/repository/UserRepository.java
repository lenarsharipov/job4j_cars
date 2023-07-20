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
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class.getName());
    private final CrudRepository crudRepository;
    public static final String USER_NOT_SAVED = "Unable to save a specified User";
    public static final String USER_NOT_UPDATED = "Unable to update a specified User";
    public static final String USER_NOT_DELETED = "Unable to delete User with specified ID";
    public static final String USERS_NOT_LISTED = "Unable to list Users";
    public static final String USER_NOT_FOUND_BY_ID = "Unable to get the User specified by ID";
    public static final String USER_NOT_FOUND_BY_LOGIN = "Unable to get User specified by login";
    public static final String USERS_NOT_FOUND_BY_LIKE_LOGIN =
            "Unable to get Users specified by login";

    public static final String DELETE = "DELETE FROM User u WHERE u.login = :fLogin";
    public static final String FIND_ALL = "FROM User u ORDER BY u.id ASC";
    public static final String FIND_BY_ID = "FROM User u WHERE u.id = :fId";
    public static final String FIND_BY_LIKE_LOGIN = "FROM User u WHERE u.login LIKE :fKey";
    public static final String FIND_BY_LOGIN = "FROM User u WHERE u.login = :fLogin";

    public static final String ID = "fId";
    public static final String KEY = "fKey";
    public static final String LOGIN = "fLogin";

    /**
     * Save User in DB.
     * @param user user.
     * @return User with specified ID.
     */
    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(user));
            result = Optional.of(user);
        } catch (Exception exception) {
            LOG.error(USER_NOT_SAVED, exception);
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
            LOG.error(USER_NOT_UPDATED, exception);
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
            result = crudRepository.isExecuted(DELETE, Map.of(LOGIN, login));
        } catch (Exception exception) {
            LOG.error(USER_NOT_DELETED, exception);
        }
        return result;
    }

    /**
     * List existed Users ordered by ID ASC.
     * @return list of users.
     */
    public List<User> findAll() {
        List<User> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, User.class);
        } catch (Exception exception) {
            LOG.error(USERS_NOT_LISTED, exception);
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
                    FIND_BY_ID, User.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(USER_NOT_FOUND_BY_ID, exception);
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
                    FIND_BY_LIKE_LOGIN, User.class,
                    Map.of(KEY, "%" + key + "%")
            );
        } catch (Exception exception) {
            LOG.error(USERS_NOT_FOUND_BY_LIKE_LOGIN, exception);
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
                    FIND_BY_LOGIN, User.class,
                    Map.of(LOGIN, login)
            );
        } catch (Exception exception) {
            LOG.error(USER_NOT_FOUND_BY_LOGIN, exception);
        }
        return result;
    }

}