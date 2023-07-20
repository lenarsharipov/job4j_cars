package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Color;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class ColorRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ColorRepository.class.getName());

    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Color c ORDER BY c.id ASC";
    private static final String FIND_BY_ID = "FROM Color c WHERE c.id = :fId";
    private static final String ID = "fId";
    private static final String COLORS_NOT_LISTED = "UNABLE TO LIST COLORS";
    private static final String COLOR_NOT_FOUND = "UNABLE TO FIND COLOR BY SPECIFIED ID";

    /**
     * List existed Colors ordered by ID ASC.
     * @return list of Colors.
     */
    public List<Color> findAll() {
        List<Color> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, Color.class);
        } catch (Exception exception) {
            LOG.error(COLORS_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Color by specified ID.
     * @return Color.
     */
    public Optional<Color> findById(int id) {
        Optional<Color> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, Color.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(COLOR_NOT_FOUND, exception);
        }
        return result;
    }
}
