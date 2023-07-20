package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Year;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class YearRepository {
    private static final Logger LOG = LoggerFactory.getLogger(YearRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Year y ORDER BY y.id ASC";
    private static final String FIND_BY_ID = "FROM Year y WHERE y.id = :fId";
    private static final String ID = "fId";
    private static final String YEARS_NOT_LISTED = "UNABLE TO LIST YEARS";
    private static final String YEAR_NOT_FOUND = "UNABLE TO FIND YEAR BY SPECIFIED ID";

    /**
     * List existed Years ordered by ID ASC.
     * @return list of Years.
     */
    public List<Year> findAll() {
        List<Year> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Year.class);
        } catch (Exception exception) {
            LOG.error(YEARS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Year by specified ID.
     * @return Year.
     */
    public Optional<Year> findById(int id) {
        Optional<Year> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Year.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(YEAR_NOT_FOUND, exception);
        }
        return result;
    }

}
