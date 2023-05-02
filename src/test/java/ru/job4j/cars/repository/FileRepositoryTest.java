package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void clear() {
        var files = FILE_REPOSITORY.findAll().stream().skip(1).toList();
        for (var file : files) {
            FILE_REPOSITORY.deleteById(file.getId());
        }
    }

    /**
     * Get optional of saved File when file saved.
     */
    @Test
    void whenCreateFileThenGetNotEmptyOptional() {
        File file = new File();
        file.setName("name");
        file.setPath("path");
        var result = FILE_REPOSITORY.save(file);
        assertThat(result).isNotEmpty();
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(DEFAULT_POSTER, result.get()));
    }

    /**
     * Get empty optional if file not saved. (Path not unique)
     */
    @Test
    void whenSavedNotUniqueFileThenGetEmptyOptional() {
        File file = new File();
        file.setName("name");
        file.setPath("path");
        FILE_REPOSITORY.save(file);
        File file2 = new File();
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
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(DEFAULT_POSTER));
        File file = new File();
        file.setName("name");
        file.setPath("path");
        FILE_REPOSITORY.save(file);
        assertThat(FILE_REPOSITORY.findAll()).isEqualTo(List.of(DEFAULT_POSTER, file));
    }

    /**
     * Get optional of searched File by ID.
     */
    @Test
    void whenGetByIdThenGetOptionalOfFile() {
        var result = FILE_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(DEFAULT_POSTER).usingRecursiveComparison();
    }

    /**
     * Get empty optional of File not found by ID.
     */
    @Test
    void whenGetByIdThenGetEmptyOptional() {
        var result = FILE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    /**
     * Get true if File deleted by id.
     */
    @Test
    void whenDeleteThenGetTrueAndFileDeleted() {
        File file = new File();
        file.setName("name");
        file.setPath("path");
        FILE_REPOSITORY.save(file);
        assertThat(FILE_REPOSITORY.deleteById(file.getId())).isTrue();
        assertThat(FILE_REPOSITORY.findAll())
                .doesNotContain(file)
                .containsOnly(DEFAULT_POSTER);
    }

    /**
     * Get false if File not deleted by id.
     */
    @Test
    void whenNotDeletedThenGetFalse() {
        assertThat(FILE_REPOSITORY.deleteById(-1)).isFalse();
        assertThat(FILE_REPOSITORY.findAll())
                .containsOnly(DEFAULT_POSTER);
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}