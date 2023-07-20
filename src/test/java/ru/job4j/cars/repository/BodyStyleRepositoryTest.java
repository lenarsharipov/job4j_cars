package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.BodyStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class BodyStyleRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final BodyStyleRepository BODY_STYLE_REPOSITORY =
            new BodyStyleRepository(CRUD_REPOSITORY);
    private static List<BodyStyle> bodyStyleList;

    @BeforeAll
    static void init() {
        var cargoVan = new BodyStyle();
        cargoVan.setId(1);
        cargoVan.setName("Cargo Van");
        var hatchback = new BodyStyle();
        hatchback.setId(2);
        hatchback.setName("Hatchback");
        var minivan = new BodyStyle();
        minivan.setId(3);
        minivan.setName("Minivan");
        var passengerVan = new BodyStyle();
        passengerVan.setId(4);
        passengerVan.setName("Passenger Van");
        bodyStyleList = new ArrayList<>(List.of(cargoVan, hatchback, minivan, passengerVan));
    }

    @Test
    void whenFindAllThenGetListOfBodyStyles() {
        var result = BODY_STYLE_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result.stream().limit(4).collect(Collectors.toList())).isEqualTo(bodyStyleList);
    }

    @Test
    void whenFindByIDThenGetBodyStyleOptional() {
        var result = BODY_STYLE_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(bodyStyleList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = BODY_STYLE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}