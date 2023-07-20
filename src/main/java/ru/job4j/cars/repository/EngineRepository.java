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
    private static final String FIND_ALL = "FROM Engine e ORDER BY e.id ASC";
    public static final String FIND_BY_ID = "FROM Engine e WHERE e.id = :fId";
    public static final String ENGINES_NOT_LISTED = "Unable to list Engines";
    public static final String ENGINE_NOT_FOUND = "Unable to get Engine with specified ID";
    public static final String ID = "fId";

    /**
     * List existed Engines ordered by ID ASC.
     * @return list of Engines.
     */
    public List<Engine> findAll() {
        List<Engine> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Engine.class);
        } catch (Exception exception) {
            LOG.error(ENGINES_NOT_LISTED, exception);
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
                    FIND_BY_ID, Engine.class, Map.of(ID, id)
            );
        } catch (Exception exception) {
            LOG.error(ENGINE_NOT_FOUND, exception);
        }
        return result;
    }
}
