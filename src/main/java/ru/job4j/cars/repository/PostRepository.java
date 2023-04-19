package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.util.Key;
import ru.job4j.cars.util.Message;
import ru.job4j.cars.util.PostQuery;

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
            LOG.error(Message.POST_NOT_SAVED, exception);
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
            LOG.error(Message.POST_NOT_UPDATED, exception);
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
            crudRepository.run(PostQuery.DELETE, Map.of(Key.F_ID, id));
        } catch (Exception exception) {
            result = false;
            LOG.error(Message.POST_NOT_DELETED, exception);
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
            result = crudRepository.query(PostQuery.FIND_ALL, Post.class);
        } catch (Exception exception) {
            LOG.error(Message.POSTS_NOT_LISTED, exception);
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
                    PostQuery.FIND_BY_ID, Post.class, Map.of(Key.F_ID, id)
            );
        } catch (Exception exception) {
            LOG.error(Message.POST_NOT_FOUND, exception);
        }
        return result;
    }

}