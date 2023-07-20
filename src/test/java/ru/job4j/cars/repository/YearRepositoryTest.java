package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Year;

import static org.assertj.core.api.Assertions.*;

class YearRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final YearRepository YEAR_REPOSITORY = new YearRepository(CRUD_REPOSITORY);

    @Test
    void whenFindAllThenGetListOfYears() {
        var result = YEAR_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        var first = new Year();
        first.setId(1);
        first.setName(1997);
        var last = new Year();
        last.setId(27);
        last.setName(2023);
        var notExisting = new Year();
        notExisting.setId(2);
        notExisting.setName(2024);
        assertThat(result).contains(first, last);
        assertThat(result).doesNotContain(notExisting);
    }

    @Test
    void whenFindByIDThenGetYearOptional() {
        var result = YEAR_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        var first = new Year();
        first.setId(1);
        first.setName(1997);
        assertThat(result.get()).isEqualTo(first).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = YEAR_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}