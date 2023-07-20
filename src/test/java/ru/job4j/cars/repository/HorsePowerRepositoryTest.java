package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.HorsePower;

import static org.assertj.core.api.Assertions.assertThat;

class HorsePowerRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final HorsePowerRepository HORSE_POWER_REPOSITORY =
            new HorsePowerRepository(CRUD_REPOSITORY);

    @Test
    void whenFindAllThenGetListOfYears() {
        var result = HORSE_POWER_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        var first = new HorsePower();
        first.setId(1);
        first.setName(109);
        var last = new HorsePower();
        last.setId(7);
        last.setName(86);
        var notExisting = new HorsePower();
        notExisting.setId(2);
        notExisting.setName(2024);
        assertThat(result).contains(first, last);
        assertThat(result).doesNotContain(notExisting);
    }

    @Test
    void whenFindByIDThenGetYearOptional() {
        var result = HORSE_POWER_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        var first = new HorsePower();
        first.setId(1);
        first.setName(109);
        assertThat(result.get()).isEqualTo(first).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = HORSE_POWER_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}