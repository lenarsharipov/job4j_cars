package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Generation;
import ru.job4j.cars.model.MakeModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class MakeModelRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MakeModelRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = """
                                FROM MakeModel m
                                ORDER BY m.id ASC
                                """;
    private static final String FIND_BY_ID = "FROM MakeModel m WHERE m.id = :fId";
    private static final String FIND_BY_MAKE_ID = "FROM MakeModel m WHERE m.make.id = :fMakeId";
    private static final String ID = "fId";
    private static final String MAKE_ID = "fMakeId";
    private static final String MAKE_MODELS_NOT_LISTED = "UNABLE TO LIST MAKE MODELS";
    private static final String MAKE_MODEL_NOT_FOUND = "UNABLE TO FIND MAKE MODEL BY SPECIFIED ID";

    /**
     * List existed MakeModels ordered by ID ASC.
     * @return list of MakeModels.
     */
    public List<MakeModel> findAll() {
        List<MakeModel> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, MakeModel.class);
        } catch (Exception exception) {
            LOG.error(MAKE_MODELS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find MakeModel by specified ID.
     * @return MakeModel.
     */
    public Optional<MakeModel> findById(int id) {
        Optional<MakeModel> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, MakeModel.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(MAKE_MODEL_NOT_FOUND, exception);
        }
        return result;
    }

    /**
     * Find MakeModel by specified MAKE ID.
     * @return MakeModel.
     */
    public List<MakeModel> findByMakeId(int makeId) {
        List<MakeModel> result = Collections.emptyList();
        try {
            result = crudRepository.query(
                    FIND_BY_MAKE_ID, MakeModel.class,
                    Map.of(MAKE_ID, makeId));
        } catch (Exception exception) {
            LOG.error(MAKE_MODELS_NOT_LISTED, exception);
        }
        return result;
    }
}
