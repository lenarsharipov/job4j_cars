package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Condition;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class ConditionRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ConditionRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Condition c ORDER BY c.id ASC";
    private static final String FIND_BY_ID = "FROM Condition c WHERE c.id = :fId";
    private static final String ID = "fId";
    private static final String CONDITIONS_NOT_LISTED = "UNABLE TO LIST CONDITIONS";
    private static final String CONDITION_NOT_FOUND = "UNABLE TO FIND CONDITION BY SPECIFIED ID";

    /**
     * List existed Conditions ordered by ID ASC.
     * @return list of Conditions.
     */
    public List<Condition> findAll() {
        List<Condition> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Condition.class);
        } catch (Exception exception) {
            LOG.error(CONDITIONS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Condition by specified ID.
     * @return Condition.
     */
    public Optional<Condition> findById(int id) {
        Optional<Condition> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Condition.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(CONDITION_NOT_FOUND, exception);
        }
        return result;
    }
}
