package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class CarRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());
    private final CrudRepository crudRepository;
    public static final String CAR_NOT_SAVED = "Unable to save a specified Car";
    public static final String CAR_NOT_UPDATED = "Unable to update a specified Car";
    public static final String CAR_NOT_DELETED = "Unable to delete Car with specified id";
    public static final String CARS_NOT_LISTED = "Unable to list cars";
    public static final String CAR_NOT_FOUND = "Unable to find a Car with specified ID";
    public static final String ID = "fId";

    public static final String DELETE = "DELETE FROM Car c WHERE c.id = :fId";

    public static final String FIND_ALL = """
            SELECT DISTINCT c
            FROM Car c
            ORDER BY c.id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT DISTINCT c
            FROM Car c
            WHERE c.id = :fId
            """;

    /**
     * Save Car in DB.
     * @param car Car.
     * @return Optional<Car>. Car shall be with specified ID.
     */
    public Optional<Car> save(Car car) {
        Optional<Car> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(car));
            result = Optional.of(car);
        } catch (Exception exception) {
            LOG.error(CAR_NOT_SAVED, exception);
        }
        return result;
    }

    /**
     * Update Car in DB.
     * @param car Car.
     */
    public boolean update(Car car) {
        var result = true;
        try {
            crudRepository.run(session -> session.update(car));
        } catch (Exception exception) {
            result = false;
            LOG.error(CAR_NOT_UPDATED, exception);
        }
        return result;
    }

    /**
     * Delete Car by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = false;
        try {
            result = crudRepository.isExecuted(DELETE, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(CAR_NOT_DELETED, exception);
        }
        return result;
    }

    /**
     * List existed Cars ordered by ID ASC.
     * @return list of Cars.
     */
    public List<Car> findAll() {
        List<Car> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Car.class);
        } catch (Exception exception) {
            LOG.error(CARS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Car by specified ID.
     * @return Car.
     */
    public Optional<Car> findById(int id) {
        Optional<Car> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Car.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(CAR_NOT_FOUND, exception);
        }
        return result;
    }
}
