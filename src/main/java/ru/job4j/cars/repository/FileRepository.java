package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Repository
public class FileRepository {
    private static final Logger LOG = LoggerFactory.getLogger(FileRepository.class.getName());
    private final CrudRepository crudRepository;
    public static final String FILE_NOT_SAVED = "Unable to save a specified File";
    public static final String FILE_NOT_DELETED = "Unable to delete File with specified ID";
    public static final String FILES_NOT_LISTED = "Unable to list Files";
    public static final String FILE_NOT_FOUND_BY_ID = "Unable to get the File specified by ID";

    public static final String FIND_BY_ID = "FROM File f WHERE f.id = :fId";
    public static final String FIND_ALL = "FROM File f ORDER BY f.id ASC";
    public static final String DELETE = "DELETE FROM File f WHERE f.id = :fId";

    public static final String ID = "fId";
    public static final String KEY = "fKey";

    /**
     * Save File in DB.
     * @param file File.
     * @return Optional of File.
     */
    public Optional<File> save(File file) {
        Optional<File> result = Optional.empty();
        try {
            crudRepository.run(session -> session.save(file));
            result = Optional.of(file);
        } catch (Exception exception) {
            LOG.error(FILE_NOT_SAVED, exception);
        }
        return result;
    }

    /**
     * List persisted Files.
     * @return List of Files.
     */
    public List<File> findAll() {
        List<File> result = Collections.emptyList();
        try {
            result = crudRepository.query(FIND_ALL, File.class);
        } catch (Exception exception) {
            LOG.error(FILES_NOT_LISTED, exception);
        }
        return result;
    }

    /**
     * Find File by specified ID.
     * @param id ID.
     * @return Optional of File.
     */
    public Optional<File> findById(int id) {
        Optional<File> result = Optional.empty();
        try {
            result = crudRepository.optional(FIND_BY_ID, File.class, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(FILE_NOT_FOUND_BY_ID, exception);
        }
        return result;
    }

    /**
     * Delete File by ID.
     * @param id ID.
     */
    public boolean deleteById(int id) {
        var result = true;
        try {
            result = crudRepository.isExecuted(DELETE, Map.of(ID, id));
        } catch (Exception exception) {
            LOG.error(FILE_NOT_DELETED, exception);
        }
        return result;
    }

}
