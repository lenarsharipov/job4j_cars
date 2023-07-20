package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.HorsePower;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class HorsePowerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(HorsePowerRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM HorsePower h ORDER BY h.id ASC";
    private static final String FIND_BY_ID = "FROM HorsePower h WHERE h.id = :fId";
    private static final String ID = "fId";
    private static final String HORSE_POWERS_NOT_LISTED = "UNABLE TO LIST HORSE POWERS";
    private static final String HORSE_POWER_NOT_FOUND =
            "UNABLE TO FIND HORSE POWER BY SPECIFIED ID";

    /**
     * List existed Years ordered by ID ASC.
     * @return list of Years.
     */
    public List<HorsePower> findAll() {
        List<HorsePower> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, HorsePower.class);
        } catch (Exception exception) {
            LOG.error(HORSE_POWERS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Year by specified ID.
     * @return Year.
     */
    public Optional<HorsePower> findById(int id) {
        Optional<HorsePower> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, HorsePower.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(HORSE_POWER_NOT_FOUND, exception);
        }
        return result;
    }
}
