package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class PriceHistoryRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PriceHistoryRepository.class);

    private final CrudRepository crudRepository;

    /**
     * Save PriceHistory in DB.
     * @param priceHistory priceHistory.
     * @return PriceHistory with specified ID.
     */
    public Optional<PriceHistory> create(PriceHistory priceHistory) {
        Optional<PriceHistory> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(priceHistory));
            result = Optional.of(priceHistory);
        } catch (Exception exception) {
            LOG.error("Unable to save a specified PriceHistory", exception);
        }
        return result;
    }

    /**
     * Update PriceHistory in DB.
     * @param priceHistory PriceHistory.
     */
    public boolean update(PriceHistory priceHistory) {
        var result = true;
        try {
            crudRepository.run(session -> session.merge(priceHistory));
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to update a specified PriceHistory", exception);
        }
        return result;
    }

    /**
     * Delete PriceHistory by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = true;
        try {
            crudRepository.run(
                    "DELETE FROM PriceHistory WHERE id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to delete the PriceHistory specified by ID ", exception);
        }
        return result;
    }

    /**
     * List existed PriceHistory ordered by ID ASC.
     * @return list of priceHistory.
     */
    public List<PriceHistory> findAllOrderById() {
        List<PriceHistory> result = Collections.emptyList();
        try {
            result = crudRepository.query("FROM PriceHistory ORDER BY id ASC", PriceHistory.class);
        } catch (Exception exception) {
            LOG.error("Unable to list PriHistories", exception);
        }
        return result;
    }

    /**
     * Find PriceHistory by specified ID.
     * @return PriceHistory.
     */
    public Optional<PriceHistory> findById(int id) {
        Optional<PriceHistory> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    "FROM PriceHistory WHERE id = :fId", PriceHistory.class,
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            LOG.error("Unable to get the PriceHistory specified by ID");
        }
        return result;
    }

}
