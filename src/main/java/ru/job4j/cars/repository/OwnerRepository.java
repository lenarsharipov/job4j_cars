package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.util.Key;
import ru.job4j.cars.util.Message;
import ru.job4j.cars.util.OwnerQuery;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class OwnerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerRepository.class);

    private final CrudRepository crudRepository;

    /**
     * Save Owner in DB.
     * @param owner Owner.
     * @return Owner with specified ID.
     */
    public Optional<Owner> save(Owner owner) {
        Optional<Owner> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(owner));
            result = Optional.of(owner);
        } catch (Exception exception) {
            LOG.error(Message.OWNER_NOT_SAVED, exception);
        }
        return result;
    }

    /**
     * Delete Owner by ID.
     * @param id ID.
     */
    public boolean delete(int id) {
        var result = true;
        try {
            result = crudRepository.isExecuted(OwnerQuery.DELETE, Map.of(Key.ID, id));
        } catch (Exception exception) {
            LOG.error(Message.OWNER_NOT_DELETED, exception);
        }
        return result;
    }

    /**
     * List existed Owners ordered by ID ASC.
     * @return list of Owners.
     */
    public List<Owner> findAll() {
        List<Owner> result = Collections.emptyList();
        try {
            result = crudRepository.query(OwnerQuery.FIND_ALL, Owner.class);
        } catch (Exception exception) {
            LOG.error(Message.OWNERS_NOT_LISTED, exception);
        }
        return result;
    }

}
