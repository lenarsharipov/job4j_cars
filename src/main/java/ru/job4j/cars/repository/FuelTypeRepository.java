package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.FuelType;
import ru.job4j.cars.model.Year;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class FuelTypeRepository {
    private static final Logger LOG = LoggerFactory.getLogger(FuelTypeRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM FuelType f ORDER BY f.id ASC";
    private static final String FIND_BY_ID = "FROM FuelType f WHERE f.id = :fId";
    private static final String ID = "fId";
    private static final String FUEL_TYPES_NOT_LISTED = "UNABLE TO LIST FUEL TYPES";
    private static final String FUEL_TYPE_NOT_FOUND = "UNABLE TO FIND FUEL TYPE BY SPECIFIED ID";

    /**
     * List existed FUEL TYPES ordered by ID ASC.
     * @return list of FUEL TYPES.
     */
    public List<FuelType> findAll() {
        List<FuelType> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, FuelType.class);
        } catch (Exception exception) {
            LOG.error(FUEL_TYPES_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Year by specified ID.
     * @return Year.
     */
    public Optional<FuelType> findById(int id) {
        Optional<FuelType> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, FuelType.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(FUEL_TYPE_NOT_FOUND, exception);
        }
        return result;
    }

}
