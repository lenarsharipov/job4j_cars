package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Trim;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class TrimRepository {
    private static final Logger LOG = LoggerFactory.getLogger(TrimRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Trim t ORDER BY t.id ASC";
    private static final String FIND_BY_ID = "FROM Trim t WHERE t.id = :fId";
    private static final String ID = "fId";
    private static final String TRIMS_NOT_LISTED = "UNABLE TO LIST TRIMS";
    private static final String TRIM_NOT_FOUND = "UNABLE TO FIND TRIM BY SPECIFIED ID";

    /**
     * List existed Trims ordered by ID ASC.
     * @return list of Trims.
     */
    public List<Trim> findAll() {
        List<Trim> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Trim.class);
        } catch (Exception exception) {
            LOG.error(TRIMS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Trim by specified ID.
     * @return Trim.
     */
    public Optional<Trim> findById(int id) {
        Optional<Trim> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Trim.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(TRIM_NOT_FOUND, exception);
        }
        return result;
    }
}
