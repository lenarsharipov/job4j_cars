package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Make;
import ru.job4j.cars.util.MakeQuery;
import ru.job4j.cars.util.Message;

import java.util.Collections;
import java.util.List;

@ThreadSafe
@AllArgsConstructor
@Repository
public class MakeRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MakeRepository.class);

    private final CrudRepository crudRepository;

    /**
     * List persisted Makes.
     * @return List of Makes.
     */
    public List<Make> findAll() {
        List<Make> result = Collections.emptyList();
        try {
            result = crudRepository.query(
                    MakeQuery.FIND_ALL, Make.class);
        } catch (Exception exception) {
            LOG.error(Message.MAKES_NOT_LISTED, exception);
        }
        return result;
    }
}
