package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.File;
import ru.job4j.cars.util.TestQuery;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final FileRepository FILE_REPOSITORY = new FileRepository(CRUD_REPOSITORY);

    @BeforeEach
    void clearTable() {
        CRUD_REPOSITORY.run(
                session -> session
                        .createSQLQuery(TestQuery.DELETE_FILE)
                        .executeUpdate());
    }

    private File createFile() {
        var file = new File();
        file.setName("name");
        file.setPath("path");
        return file;
    }

    /**
     * Get optional of saved File when file saved.
     */
    @Test
    void whenCreateFileThenGetNotEmptyOptional() {
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
        var file = createFile();
        var result = FILE_REPOSITORY.save(file);
        assertThat(result).isNotEmpty();
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(result.get()));
    }

    /**
     * Get empty optional if file not saved. (Path not unique)
     */
    @Test
    void whenSavedNotUniqueFileThenGetEmptyOptional() {
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
        var file = createFile();
        FILE_REPOSITORY.save(file);
        var file2 = createFile();
        file2.setName("name2");
        file2.setPath("path");
        var result = FILE_REPOSITORY.save(file2);
        assertThat(result).isEmpty();
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(file));
    }

    /**
     * Get list of all persisted files.
     */
    @Test
    void whenFindAllThenGetPersistedFilesList() {
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
        var file = createFile();
        FILE_REPOSITORY.save(file);
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(file));
    }

    /**
     * Get optional of searched File by ID.
     */
    @Test
    void whenGetByIdThenGetOptionalOfFile() {
        var file = createFile();
        FILE_REPOSITORY.save(file);
        var result = FILE_REPOSITORY.findById(file.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(file).usingRecursiveComparison();
    }

    /**
     * Get empty optional of File not found by ID.
     */
    @Test
    void whenGetByIdThenGetEmptyOptional() {
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
        var result = FILE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    /**
     * Get true if File deleted by id.
     */
    @Test
    void whenDeleteThenGetTrueAndFileDeleted() {
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
        var file = createFile();
        FILE_REPOSITORY.save(file);
        assertThat(FILE_REPOSITORY.deleteById(file.getId())).isTrue();
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
    }

    /**
     * Get false if File not deleted by id.
     */
    @Test
    void whenNotDeletedThenGetFalse() {
        assertThat(FILE_REPOSITORY.deleteById(-1)).isFalse();
        assertThat(FILE_REPOSITORY.findAll()).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}