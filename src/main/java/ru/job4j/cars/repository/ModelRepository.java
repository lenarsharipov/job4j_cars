package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class ModelRepository {
    private static final Logger LOG = LoggerFactory.getLogger(YearRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Model m ORDER BY m.id ASC";
    private static final String FIND_BY_ID =
            "FROM Model m LEFT JOIN FETCH m.bodyStyles WHERE m.id = :fId";
    private static final String ID = "fId";
    private static final String MODELS_NOT_LISTED = "UNABLE TO LIST MODELS";
    private static final String MODEL_NOT_FOUND = "UNABLE TO FIND MODEL BY SPECIFIED ID";

    /**
     * List existed Models ordered by ID ASC.
     * @return list of Models.
     */
    public List<Model> findAll() {
        List<Model> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Model.class);
        } catch (Exception exception) {
            LOG.error(MODELS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Model by specified ID.
     * @return Model.
     */
    public Optional<Model> findById(int id) {
        Optional<Model> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Model.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(MODEL_NOT_FOUND, exception);
        }
        return result;
    }
}
