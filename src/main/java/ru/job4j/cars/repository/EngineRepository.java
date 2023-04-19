package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class EngineRepository {

    private static final Logger LOG = LoggerFactory.getLogger(EngineRepository.class.getName());

    private final CrudRepository crudRepository;

    /**
     * Save Engine in DB.
     * @param engine Engine.
     * @return Engine with specified ID.
     */
    public Optional<Engine> create(Engine engine) {
        Optional<Engine> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(engine));
            result = Optional.of(engine);
        } catch (Exception exception) {
            LOG.error("Unable to save a specified engine", exception);
        }
        return result;
    }

    /**
     * Update Engine in DB.
     * @param engine Engine.
     */
    public boolean update(Engine engine) {
        var result = true;
        try {
            crudRepository.run(session -> session.merge(engine));
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to update a specified Engine", exception);
        }
        return result;
    }

    /**
     * Delete Engine by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = true;
        try {
            crudRepository.run(
                    "DELETE FROM Engine e WHERE e.id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to delete an engine with specified ID");
        }
        return result;
    }

    /**
     * List existed Engines ordered by ID ASC.
     * @return list of Engines.
     */
    public List<Engine> findAllOrderById() {
        List<Engine> result = Collections.emptyList();
        try {
            result = crudRepository.query("FROM Engine e ORDER BY e.id ASC", Engine.class);
        } catch (Exception exception) {
            LOG.error("Unable to list Engines", exception);
        }
        return result;
    }

    /**
     * Find Engine by specified ID.
     * @return Engine.
     */
    public Optional<Engine> findById(int id) {
        Optional<Engine> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    "FROM Engine WHERE id = :fId", Engine.class,
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            LOG.error("Unable to get Engine with specified ID");
        }
        return result;
    }
}
