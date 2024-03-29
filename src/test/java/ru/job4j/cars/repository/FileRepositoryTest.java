package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.File;

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
    private static final File DEFAULT_POSTER = FILE_REPOSITORY.findAll().get(0);

    @BeforeEach
    void clearTable() {
        var files = FILE_REPOSITORY.findAll();
        for (var index = 1; index < files.size(); index++) {
            FILE_REPOSITORY.deleteById(files.get(index).getId());
        }
    }

    private File createTestFile() {
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
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
        var file = createTestFile();
        var result = FILE_REPOSITORY.save(file);
        assertThat(result).isNotEmpty();
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER, result.get()))
                .usingRecursiveComparison();
    }

    /**
     * Get empty optional if file not saved. (Path not unique)
     */
    @Test
    void whenSavedNotUniqueFileThenGetEmptyOptional() {
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
        var file = createTestFile();
        FILE_REPOSITORY.save(file);
        var file2 = createTestFile();
        file2.setName("name2");
        file2.setPath("path");
        var result = FILE_REPOSITORY.save(file2);
        assertThat(result).isEmpty();
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(DEFAULT_POSTER, file));
    }

    /**
     * Get list of all persisted files.
     */
    @Test
    void whenFindAllThenGetPersistedFilesList() {
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
        var file = createTestFile();
        FILE_REPOSITORY.save(file);
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(DEFAULT_POSTER, file));
    }

    /**
     * Get optional of File found by ID.
     */
    @Test
    void whenGetByIdThenGetOptionalOfFile() {
        var file = createTestFile();
        FILE_REPOSITORY.save(file);
        var result = FILE_REPOSITORY.findById(file.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(file).usingRecursiveComparison();
    }

    /**
     * Get empty optional if File not found by ID.
     */
    @Test
    void whenGetByIdThenGetEmptyOptional() {
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
        var result = FILE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    /**
     * Get true if File deleted by id.
     */
    @Test
    void whenDeleteThenGetTrueAndFileDeleted() {
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
        var file = createTestFile();
        FILE_REPOSITORY.save(file);
        assertThat(FILE_REPOSITORY.deleteById(file.getId())).isTrue();
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
    }

    /**
     * Get false if File not deleted by id.
     */
    @Test
    void whenNotDeletedThenGetFalse() {
        assertThat(FILE_REPOSITORY.deleteById(-1)).isFalse();
        assertThat(FILE_REPOSITORY.findAll())
                .isEqualTo(List.of(DEFAULT_POSTER))
                .usingRecursiveComparison();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}