package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Drivetrain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class DrivetrainRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DrivetrainRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Drivetrain d ORDER BY d.id ASC";
    private static final String FIND_BY_ID = "FROM Drivetrain d WHERE d.id = :fId";
    private static final String ID = "fId";
    private static final String DRIVETRAINS_NOT_LISTED = "UNABLE TO LIST DRIVETRAINS";
    private static final String DRIVETRAIN_NOT_FOUND = "UNABLE TO FIND DRIVETRAIN BY SPECIFIED ID";

    /**
     * List existed Drivetrains ordered by ID ASC.
     * @return list of Years.
     */
    public List<Drivetrain> findAll() {
        List<Drivetrain> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Drivetrain.class);
        } catch (Exception exception) {
            LOG.error(DRIVETRAINS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Drivetrain by specified ID.
     * @return Drivetrain.
     */
    public Optional<Drivetrain> findById(int id) {
        Optional<Drivetrain> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Drivetrain.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(DRIVETRAIN_NOT_FOUND, exception);
        }
        return result;
    }
}
