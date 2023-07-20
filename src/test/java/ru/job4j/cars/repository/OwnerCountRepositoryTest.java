package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.OwnerCount;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerCountRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final OwnerCountRepository OWNER_COUNT_REPOSITORY =
            new OwnerCountRepository(CRUD_REPOSITORY);
    private static List<OwnerCount> ownerCountList;

    @BeforeAll
    static void init() {
        var one = new OwnerCount();
        one.setId(1);
        one.setCount("1");
        var two = new OwnerCount();
        two.setId(2);
        two.setCount("2");
        var three = new OwnerCount();
        three.setId(3);
        three.setCount("3");
        var four = new OwnerCount();
        four.setId(4);
        four.setCount("4+");
        ownerCountList = List.of(one, two, three, four);
    }

    @Test
    void whenFindAllThenGetListOfGenerations() {
        var result = OWNER_COUNT_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(ownerCountList).usingRecursiveComparison();
    }

    @Test
    void whenFindByIDThenGetGenerationOptional() {
        var result = OWNER_COUNT_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(ownerCountList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = OWNER_COUNT_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}