package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.EngineCapacity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class EngineCapacityRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(EngineCapacityRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM EngineCapacity e ORDER BY e.id ASC";
    private static final String FIND_BY_ID = "FROM EngineCapacity e WHERE e.id = :fId";
    private static final String ID = "fId";
    private static final String ENGINE_CAPACITIES_NOT_LISTED = "UNABLE TO LIST ENGINE CAPACITIES";
    private static final String ENGINE_CAPACITY_NOT_FOUND =
            "UNABLE TO FIND ENGINE CAPACITY BY SPECIFIED ID";

    /**
     * List existed EngineCapacities ordered by ID ASC.
     * @return list of EngineCapacities.
     */
    public List<EngineCapacity> findAll() {
        List<EngineCapacity> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, EngineCapacity.class);
        } catch (Exception exception) {
            LOG.error(ENGINE_CAPACITIES_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find EngineCapacity by specified ID.
     * @return EngineCapacity.
     */
    public Optional<EngineCapacity> findById(int id) {
        Optional<EngineCapacity> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, EngineCapacity.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(ENGINE_CAPACITY_NOT_FOUND, exception);
        }
        return result;
    }
}
