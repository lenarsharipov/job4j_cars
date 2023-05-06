package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.util.CarQuery;
import ru.job4j.cars.util.Key;
import ru.job4j.cars.util.Message;

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
    public Optional<Car> save(Car car) {
        Optional<Car> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(car));
            result = Optional.of(car);
        } catch (Exception exception) {
            LOG.error(Message.CAR_NOT_SAVED, exception);
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
            LOG.error(Message.CAR_NOT_UPDATED, exception);
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
            result = crudRepository.isExecuted(CarQuery.DELETE, Map.of(Key.ID, id));
        } catch (Exception exception) {
            LOG.error(Message.CAR_NOT_DELETED, exception);
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
            result = crudRepository.query(CarQuery.FIND_ALL, Car.class);
        } catch (Exception exception) {
            LOG.error(Message.CARS_NOT_LISTED, exception);
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
                    CarQuery.FIND_BY_ID, Car.class, Map.of(Key.ID, id)
            );
        } catch (Exception exception) {
            LOG.error(Message.CAR_NOT_FOUND, exception);
        }
        return result;
    }
}
