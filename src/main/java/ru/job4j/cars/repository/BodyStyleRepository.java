package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyStyle;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class BodyStyleRepository {
    private static final Logger LOG = LoggerFactory.getLogger(BodyStyleRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM BodyStyle b ORDER BY b.id ASC";
    private static final String FIND_BY_ID = "FROM BodyStyle b WHERE b.id = :fId";
    private static final String ID = "fId";
    private static final String BODY_STYLES_NOT_LISTED = "UNABLE TO LIST BODY STYLES";
    private static final String BODY_STYLE_NOT_FOUND = "UNABLE TO FIND BODY STYLE BY SPECIFIED ID";

    /**
     * List existed BodyStyle ordered by ID ASC.
     * @return list of BodyStyle.
     */
    public List<BodyStyle> findAll() {
        List<BodyStyle> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, BodyStyle.class);
        } catch (Exception exception) {
            LOG.error(BODY_STYLES_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find BodyStyle by specified ID.
     * @return BodyStyle.
     */
    public Optional<BodyStyle> findById(int id) {
        Optional<BodyStyle> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, BodyStyle.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(BODY_STYLE_NOT_FOUND, exception);
        }
        return result;
    }
}
