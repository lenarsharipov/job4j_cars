package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class TransmissionRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(TransmissionRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Transmission t ORDER BY t.id ASC";
    private static final String FIND_BY_ID = "FROM Transmission t WHERE t.id = :fId";
    private static final String ID = "fId";
    private static final String TRANSMISSIONS_NOT_LISTED = "UNABLE TO LIST TRANSMISSIONS";
    private static final String TRANSMISSION_NOT_FOUND =
            "UNABLE TO FIND TRANSMISSION BY SPECIFIED ID";

    /**
     * List existed Transmissions ordered by ID ASC.
     * @return list of Transmissions.
     */
    public List<Transmission> findAll() {
        List<Transmission> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Transmission.class);
        } catch (Exception exception) {
            LOG.error(TRANSMISSIONS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Transmission by specified ID.
     * @return Transmission.
     */
    public Optional<Transmission> findById(int id) {
        Optional<Transmission> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Transmission.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(TRANSMISSION_NOT_FOUND, exception);
        }
        return result;
    }
}
