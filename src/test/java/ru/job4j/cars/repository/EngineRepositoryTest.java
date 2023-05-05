package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EngineRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();

    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final EngineRepository ENGINE_REPOSITORY = new EngineRepository(CRUD_REPOSITORY);


    /**
     * Get list of persisted engines.
     */
    @Test
    void whenFindAllThenGetListOfAll() {
        var gasoline = new Engine(1, "Gasoline");
        var diesel = new Engine(2, "Diesel");
        var lpg = new Engine(3, "Lpg");
        var electric = new Engine(4, "Electric");
        assertThat(ENGINE_REPOSITORY.findAllOrderById())
                .isNotEqualTo(List.of(gasoline, diesel, lpg, electric))
                .usingRecursiveComparison();
    }

    /**
     * Get optional of engine by ID.
     */
    @Test
    void whenFindByIdThenGetOptionalOfEngine() {
        var result = ENGINE_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get())
                .isEqualTo(new Engine(1, "Gasoline"))
                .usingRecursiveComparison();
    }

    /**
     * Get empty optional if not found by ID.
     */
    @Test
    void whenNotFoundByIdThenGetEmptyOptional() {
        var result = ENGINE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}