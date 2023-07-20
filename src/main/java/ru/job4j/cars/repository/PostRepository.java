package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class PostRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PostRepository.class.getName());
    private final CrudRepository crudRepository;
    public static final String POST_NOT_SAVED = "Unable to save a specified Post";
    public static final String POST_NOT_UPDATED = "Unable to update a specified Post";
    public static final String POST_NOT_DELETED = "Unable to delete Post with specified ID";
    public static final String POSTS_NOT_LISTED = "Unable to list Posts";
    public static final String POST_NOT_FOUND = "Unable to get Post with specified ID";
    public static final String DELETE = "DELETE FROM Post p WHERE p.id = :fId";

    public static final String FIND_ALL = """
            SELECT DISTINCT p
            FROM Post p
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT DISTINCT p
            FROM Post p
            WHERE p.id = :fId
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_TODAY = """
            SELECT DISTINCT p
            FROM Post p
            WHERE p.created >= :fCreated
            ORDER BY p.id ASC
            """;

    public static final String FIND_ALL_WITH_PHOTO = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.car
            WHERE p.hasPhoto = true
            ORDER BY p.id ASC
            """;
    public static final String FIND_BY_MAKE = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.car
            WHERE p.car.modification.make.name = :fName
            ORDER BY p.id ASC
            """;

    public static final String ID = "fId";
    public static final String KEY = "fKey";
    public static final String CREATED = "fCreated";
    public static final String MAKE_NAME = "fName";

    /**
     * Save Post in DB.
     * @param post Post.
     * @return Post with specified ID.
     */
    public Optional<Post> save(Post post) {
        Optional<Post> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(post));
            result = Optional.of(post);
        } catch (Exception exception) {
            LOG.error(POST_NOT_SAVED, exception);
        }
        return result;
    }

    /**
     * Update Post in DB.
     * @param post Post.
     */
    public boolean update(Post post) {
        var result = true;
        try {
            crudRepository.run(session -> session.update(post));
        } catch (Exception exception) {
            result = false;
            LOG.error(POST_NOT_UPDATED, exception);
        }
        return result;
    }

    /**
     * Delete Post by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = false;
        try {
            result = crudRepository.isExecuted(DELETE, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(POST_NOT_DELETED, exception);
        }
        return result;
    }

    /**
     * List existed Posts ordered by ID ASC.
     * @return list of Posts.
     */
    public List<Post> findAll() {
        List<Post> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Post.class);
        } catch (Exception exception) {
            LOG.error(POSTS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Post by specified ID.
     * @return Post.
     */
    public Optional<Post> findById(int id) {
        Optional<Post> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Post.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(POST_NOT_FOUND, exception);
        }
        return result;
    }

    /**
     * Find list of today's Post.
     * @return Post list.
     */
    public List<Post> findTodayPosts() {
        List<Post> result = Collections.emptyList();
        try {
            result = crudRepository.query(
                    FIND_BY_TODAY, Post.class,
                    Map.of(CREATED, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
            );
        } catch (Exception exception) {
            LOG.error(POST_NOT_FOUND, exception);
        }
        return result;
    }

    /**
     * List Posts with photos.
     * @return Post list.
     */
    public List<Post> findWithPhoto() {
        List<Post> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL_WITH_PHOTO, Post.class);
        } catch (Exception exception) {
            LOG.error(POSTS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * List Posts found by make.
     * @return Post list.
     */
    public List<Post> findByMake(String make) {
        List<Post> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_BY_MAKE, Post.class, Map.of(MAKE_NAME, make));
        } catch (Exception exception) {
            LOG.error(POSTS_NOT_LISTED, exception);
        }
        return result;
    }

}