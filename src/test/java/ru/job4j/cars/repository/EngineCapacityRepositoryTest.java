package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.EngineCapacity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EngineCapacityRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final EngineCapacityRepository ENGINE_CAPACITY_REPOSITORY =
            new EngineCapacityRepository(CRUD_REPOSITORY);
    private static List<EngineCapacity> engineCapacityList;

    @BeforeAll
    static void init() {
        var first = new EngineCapacity();
        first.setId(1);
        first.setLitres("1.3");
        var second = new EngineCapacity();
        second.setId(2);
        second.setLitres("1.5");
        var third = new EngineCapacity();
        third.setId(3);
        third.setLitres("1.6");
        var fourth = new EngineCapacity();
        fourth.setId(4);
        fourth.setLitres("2.0");
        engineCapacityList = new ArrayList<>(List.of(first, second, third, fourth));
    }

    @Test
    void whenFindAllThenGetListOfPersistedEngineCapacities() {
        var result = ENGINE_CAPACITY_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(engineCapacityList);
    }

    @Test
    void whenFindByIDThenGetEngineCapacityOptional() {
        var result = ENGINE_CAPACITY_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(engineCapacityList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = ENGINE_CAPACITY_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}