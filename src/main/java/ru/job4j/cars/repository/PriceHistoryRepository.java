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
    private static final Logger LOG =
            LoggerFactory.getLogger(PriceHistoryRepository.class.getName());

    private final CrudRepository crudRepository;
    public static final String PRICE_HISTORY_NOT_SAVED =
            "Unable to save a specified PriceHistory";
    public static final String PRICE_HISTORY_NOT_UPDATED =
            "Unable to update a specified PriceHistory";
    public static final String PRICE_HISTORY_NOT_DELETED =
            "Unable to delete PriceHistory with specified ID";
    public static final String PRICE_HISTORIES_NOT_LISTED =
            "Unable to list PriceHistories";
    public static final String PRICE_HISTORY_NOT_FOUND =
            "Unable to get PriceHistory with specified ID";

    public static final String DELETE = "DELETE FROM PriceHistory p WHERE p.id = :fId";
    public static final String FIND_ALL = "FROM PriceHistory p ORDER BY p.id ASC";
    public static final String FIND_BY_ID = "FROM PriceHistory p WHERE p.id = :fId";
    public static final String ID = "fId";
    public static final String KEY = "fKey";

    /**
     * Save PriceHistory in DB.
     * @param priceHistory priceHistory.
     * @return PriceHistory with specified ID.
     */
    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        Optional<PriceHistory> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(priceHistory));
            result = Optional.of(priceHistory);
        } catch (Exception exception) {
            LOG.error(PRICE_HISTORY_NOT_SAVED, exception);
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
            crudRepository.run(session -> session.update(priceHistory));
        } catch (Exception exception) {
            result = false;
            LOG.error(PRICE_HISTORY_NOT_UPDATED, exception);
        }
        return result;
    }

    /**
     * Delete PriceHistory by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = false;
        try {
            result = crudRepository.isExecuted(DELETE, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(PRICE_HISTORY_NOT_DELETED, exception);
        }
        return result;
    }

    /**
     * List existed PriceHistory ordered by ID ASC.
     * @return list of priceHistory.
     */
    public List<PriceHistory> findAll() {
        List<PriceHistory> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, PriceHistory.class);
        } catch (Exception exception) {
            LOG.error(PRICE_HISTORIES_NOT_LISTED, exception);
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
            result = crudRepository.optional(FIND_BY_ID, PriceHistory.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(PRICE_HISTORY_NOT_FOUND, exception);
        }
        return result;
    }

}
