package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Make;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class MakeRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MakeRepository.class.getName());
    private final CrudRepository crudRepository;
    public static final String FIND_ALL =
        """
        FROM Make m
        ORDER BY m.id ASC
        """;
    public static final String FIND_BY_ID =
        """
        FROM Make m
        LEFT JOIN FETCH m.models
        WHERE m.id = :fId
        """;
    public static final String ID = "fId";
    public static final String MAKES_NOT_LISTED = "Unable to list Makes";
    public static final String MAKE_NOT_FOUND = "Unable to find Make by Id";

    /**
     * List persisted Makes.
     * @return List of Makes.
     */
    public List<Make> findAll() {
        List<Make> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Make.class);
        } catch (Exception exception) {
            LOG.error(MAKES_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Make by specified ID.
     * @return Make.
     */
    public Optional<Make> findById(int id) {
        Optional<Make> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Make.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(MAKE_NOT_FOUND, exception);
        }
        return result;
    }
}
