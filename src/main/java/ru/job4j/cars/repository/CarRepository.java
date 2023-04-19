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

    /**
     * Save Car in DB.
     * @param car Car.
     * @return Optional<Car>. Car shall be with specified ID.
     */
    public Optional<Car> create(Car car) {
        Optional<Car> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(car));
            result = Optional.of(car);
        } catch (Exception exception) {
            LOG.error("Unable to save a specified Task", exception);
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
            crudRepository.run(session -> session.merge(car));
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to update a specified Car", exception);
        }
        return result;
    }

    /**
     * Delete Car by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = true;
        try {
            crudRepository.run(
                    "DELETE FROM Car c WHERE c.id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to delete Car with specified id", exception);
        }
        return result;
    }

    /**
     * List existed Cars ordered by ID ASC.
     * @return list of Cars.
     */
    public List<Car> findAllOrderById() {
        List<Car> result = Collections.emptyList();
        try {
            result = crudRepository.query(
                    """
                            SELECT DISTINCT c
                            FROM Car c
                            LEFT JOIN FETCH c.owners
                            ORDER BY id ASC
                            """, Car.class);
        } catch (Exception exception) {
            LOG.error("Unable to list cars", exception);
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
            result = crudRepository.optional(
                    """
                            SELECT DISTINCT c
                            FROM Car c
                            JOIN FETCH p.owners
                            WHERE id = :fId
                            ORDER BY id ASC
                            """, Car.class,
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            LOG.error("Unable to find a Task with specified ID");
        }
        return result;
    }
}
