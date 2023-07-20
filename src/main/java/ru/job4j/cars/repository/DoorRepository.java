package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Door;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class DoorRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DoorRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Door d ORDER BY d.id ASC";
    private static final String FIND_BY_ID = "FROM Door d WHERE d.id = :fId";
    private static final String ID = "fId";
    private static final String DOORS_NOT_LISTED = "UNABLE TO LIST DOORS";
    private static final String DOOR_NOT_FOUND = "UNABLE TO FIND DOOR BY SPECIFIED ID";

    /**
     * List existed Doors ordered by ID ASC.
     * @return list of Doors.
     */
    public List<Door> findAll() {
        List<Door> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Door.class);
        } catch (Exception exception) {
            LOG.error(DOORS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Door by specified ID.
     * @return Door.
     */
    public Optional<Door> findById(int id) {
        Optional<Door> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Door.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(DOOR_NOT_FOUND, exception);
        }
        return result;
    }

}
