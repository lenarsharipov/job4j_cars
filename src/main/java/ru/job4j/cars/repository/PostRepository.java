package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class PostRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PostRepository.class);

    private final CrudRepository crudRepository;

    /**
     * Save Post in DB.
     * @param post Post.
     * @return Post with specified ID.
     */
    public Optional<Post> create(Post post) {
        Optional<Post> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(post));
            result = Optional.of(post);
        } catch (Exception exception) {
            LOG.error("Unable to save a specified Post", exception);
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
            crudRepository.run(session -> session.merge(post));
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to update a specified Post", exception);
        }
        return result;
    }

    /**
     * Delete Post by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = true;
        try {
            crudRepository.run(
                    "DELETE FROM Post WHERE id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to delete Post with specified ID", exception);
        }
        return result;
    }

    /**
     * List existed Posts ordered by ID ASC.
     * @return list of Posts.
     */
    public List<Post> findAllOrderById() {
        List<Post> result = Collections.emptyList();
        try {
            result = crudRepository.query(
                    """
                            SELECT DISTINCT p
                            FROM Post p
                            LEFT JOIN FETCH p.priceHistories
                            LEFT JOIN FETCH p.participates
                            ORDER BY id ASC
                            """, Post.class);
        } catch (Exception exception) {
            LOG.error("Unable to list Posts", exception);
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
            result = crudRepository.optional(
                    """
                            SELECT DISTINCT p
                            FROM Post p
                            LEFT JOIN FETCH p.priceHistories
                            LEFT JOIN FETCH p.participates
                            WHERE id = :fId
                            ORDER BY id ASC
                            """, Post.class,
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            LOG.error("Unable to get the Post specified by ID", exception);
        }
        return result;
    }

}