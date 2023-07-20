package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.MakeModelGeneration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class MakeModelGenerationRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(MakeModelGenerationRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM MakeModelGeneration m ORDER BY m.id ASC";
    private static final String FIND_BY_ID = "FROM MakeModelGeneration m WHERE m.id = :fId";
    private static final String ID = "fId";
    private static final String MAKE_MODEL_GENERATIONS_NOT_LISTED =
            "UNABLE TO LIST MAKE_MODEL_GENERATIONS";
    private static final String MAKE_MODEL_GENERATION_NOT_FOUND =
            "UNABLE TO FIND MAKE_MODEL_GENERATION BY SPECIFIED ID";

    /**
     * List existed MAKE_MODEL_GENERATIONS ordered by ID ASC.
     * @return list of MAKE_MODEL_GENERATIONS.
     */
    public List<MakeModelGeneration> findAll() {
        List<MakeModelGeneration> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, MakeModelGeneration.class);
        } catch (Exception exception) {
            LOG.error(MAKE_MODEL_GENERATIONS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find MakeModelGeneration by specified ID.
     * @return MakeModelGeneration.
     */
    public Optional<MakeModelGeneration> findById(int id) {
        Optional<MakeModelGeneration> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, MakeModelGeneration.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(MAKE_MODEL_GENERATION_NOT_FOUND, exception);
        }
        return result;
    }

}
