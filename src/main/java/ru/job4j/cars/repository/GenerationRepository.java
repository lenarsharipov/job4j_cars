package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Generation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class GenerationRepository {
    private static final Logger LOG = LoggerFactory.getLogger(GenerationRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Generation g ORDER BY g.id ASC";
    private static final String FIND_BY_ID = "FROM Generation g WHERE g.id = :fId";
    private static final String ID = "fId";
    private static final String GENERATIONS_NOT_LISTED = "UNABLE TO LIST GENERATIONS";
    private static final String GENERATION_NOT_FOUND = "UNABLE TO FIND GENERATION BY SPECIFIED ID";

    /**
     * List existed Generations ordered by ID ASC.
     * @return list of Generations.
     */
    public List<Generation> findAll() {
        List<Generation> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Generation.class);
        } catch (Exception exception) {
            LOG.error(GENERATIONS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Generation by specified ID.
     * @return Generation.
     */
    public Optional<Generation> findById(int id) {
        Optional<Generation> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Generation.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(GENERATION_NOT_FOUND, exception);
        }
        return result;
    }
}
