package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.util.EngineQuery;
import ru.job4j.cars.util.Key;
import ru.job4j.cars.util.Message;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class EngineRepository {

    private static final Logger LOG = LoggerFactory.getLogger(EngineRepository.class.getName());

    private final CrudRepository crudRepository;

//    /**
//     * Save Engine in DB.
//     * @param engine Engine.
//     * @return Engine with specified ID.
//     */
//    public Optional<Engine> create(Engine engine) {
//        Optional<Engine> result = Optional.empty();
//        try {
//            crudRepository.run(session -> session.save(engine));
//            result = Optional.of(engine);
//        } catch (Exception exception) {
//            LOG.error(Message.ENGINE_NOT_SAVED, exception);
//        }
//        return result;
//    }
//
//    /**
//     * Update Engine in DB.
//     * @param engine Engine.
//     */
//    public boolean update(Engine engine) {
//        var result = true;
//        try {
//            crudRepository.run(session -> session.update(engine));
//        } catch (Exception exception) {
//            result = false;
//            LOG.error(Message.ENGINE_NOT_UPDATED, exception);
//        }
//        return result;
//    }
//
//    /**
//     * Delete Engine by ID.
//     * @param id ID.
//     */
//    public boolean delete(int id) {
//        var result = false;
//        try {
//            result = crudRepository.isExecuted(EngineQuery.DELETE, Map.of(Key.F_ID, id));
//        } catch (Exception exception) {
//            LOG.error(Message.ENGINE_NOT_DELETED, exception);
//        }
//        return result;
//    }

    /**
     * List existed Engines ordered by ID ASC.
     * @return list of Engines.
     */
    public List<Engine> findAllOrderById() {
        List<Engine> result = Collections.emptyList();
        try {
            result = crudRepository.query(EngineQuery.FIND_ALL, Engine.class);
        } catch (Exception exception) {
            LOG.error(Message.ENGINES_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find Engine by specified ID.
     * @return Engine.
     */
    public Optional<Engine> findById(int id) {
        Optional<Engine> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    EngineQuery.FIND_BY_ID, Engine.class,
                    Map.of(Key.ID, id)
            );
        } catch (Exception exception) {
            LOG.error(Message.ENGINE_NOT_FOUND, exception);
        }
        return result;
    }
}
