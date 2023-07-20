package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Cylinder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class CylinderRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CylinderRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Cylinder c ORDER BY c.id ASC";
    private static final String FIND_BY_ID = "FROM Cylinder c WHERE c.id = :fId";
    private static final String ID = "fId";
    private static final String CYLINDERS_NOT_LISTED = "UNABLE TO LIST CYLINDERS";
    private static final String CYLINDER_NOT_FOUND = "UNABLE TO FIND CYLINDER BY SPECIFIED ID";

    /**
     * List existed Cylinders ordered by ID ASC.
     * @return list of Cylinders.
     */
    public List<Cylinder> findAll() {
        List<Cylinder> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Cylinder.class);
        } catch (Exception exception) {
            LOG.error(CYLINDERS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Cylinder by specified ID.
     * @return Cylinder.
     */
    public Optional<Cylinder> findById(int id) {
        Optional<Cylinder> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Cylinder.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(CYLINDER_NOT_FOUND, exception);
        }
        return result;
    }
}
