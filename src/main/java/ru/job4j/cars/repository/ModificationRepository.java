package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Modification;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class ModificationRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(ModificationRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Modification m ORDER BY m.id ASC";
    private static final String FIND_BY_ID = "FROM Modification m WHERE m.id = :fId";
    private static final String ID = "fId";
    private static final String MODIFICATIONS_NOT_LISTED = "UNABLE TO LIST MODIFICATIONS";
    private static final String MODIFICATION_NOT_FOUND =
            "UNABLE TO FIND MODIFICATION BY SPECIFIED ID";

    /**
     * List existed MODIFICATIONS ordered by ID ASC.
     * @return list of MODIFICATION.
     */
    public List<Modification> findAll() {
        List<Modification> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Modification.class);
        } catch (Exception exception) {
            LOG.error(MODIFICATIONS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find MODIFICATION by specified ID.
     * @return MODIFICATION.
     */
    public Optional<Modification> findById(int id) {
        Optional<Modification> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Modification.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(MODIFICATION_NOT_FOUND, exception);
        }
        return result;
    }
}
