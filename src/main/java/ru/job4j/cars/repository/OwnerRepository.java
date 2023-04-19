package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

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
    public Optional<Owner> create(Owner owner) {
        Optional<Owner> result = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(owner));
            result = Optional.of(owner);
        } catch (Exception exception) {
            LOG.error("Unable to save a specified engine", exception);
        }
        return result;
    }

    /**
     * Update Owner in DB.
     * @param owner Owner.
     */
    public boolean update(Owner owner) {
        var result = true;
        try {
            crudRepository.run(session -> session.merge(owner));
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to update a specified Owner", exception);
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
            crudRepository.run(
                    "DELETE FROM Owner o WHERE o.id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            result = false;
            LOG.error("Unable to delete Owner with specified ID", exception);
        }
        return result;
    }

    /**
     * List existed Owners ordered by ID ASC.
     * @return list of Owners.
     */
    public List<Owner> findAllOrderById() {
        List<Owner> result = Collections.emptyList();
        try {
            result = crudRepository.query("FROM Owner o ORDER BY o.id ASC", Owner.class);
        } catch (Exception exception) {
            LOG.error("Unable to list Owners", exception);
        }
        return result;
    }

    /**
     * Find Owner by specified ID.
     * @return Owner.
     */
    public Optional<Owner> findById(int id) {
        Optional<Owner> result = Optional.empty();
        try {
            result = crudRepository.optional(
                    "FROM Owner o WHERE o.id = :fId", Owner.class,
                    Map.of("fId", id)
            );
        } catch (Exception exception) {
            LOG.error("Unable to get Owner with specified ID", exception);
        }
        return result;
    }

}
