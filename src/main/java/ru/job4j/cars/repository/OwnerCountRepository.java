package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.OwnerCount;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class OwnerCountRepository {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerCountRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM OwnerCount o ORDER BY o.id ASC";
    private static final String FIND_BY_ID = "FROM OwnerCount o WHERE o.id = :fId";
    private static final String ID = "fId";
    private static final String OWNER_COUNTS_NOT_LISTED = "UNABLE TO LIST OWNER_COUNTS";
    private static final String OWNER_COUNT_NOT_FOUND =
            "UNABLE TO FIND OWNER_COUNT BY SPECIFIED ID";

    /**
     * List existed OwnerCounts ordered by ID ASC.
     * @return list of OwnerCounts.
     */
    public List<OwnerCount> findAll() {
        List<OwnerCount> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, OwnerCount.class);
        } catch (Exception exception) {
            LOG.error(OWNER_COUNTS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find OwnerCount by specified ID.
     * @return OwnerCount.
     */
    public Optional<OwnerCount> findById(int id) {
        Optional<OwnerCount> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, OwnerCount.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(OWNER_COUNT_NOT_FOUND, exception);
        }
        return result;
    }
}
