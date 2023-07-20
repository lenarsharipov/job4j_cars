package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Color;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColorRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final ColorRepository COLOR_REPOSITORY = new ColorRepository(CRUD_REPOSITORY);
    private static List<Color> colorList;

    @BeforeAll
    static void init() {
        var black = new Color();
        black.setId(1);
        black.setName("Black");
        var blue = new Color();
        blue.setId(2);
        blue.setName("Blue");
        var gray = new Color();
        gray.setId(3);
        gray.setName("Gray");
        var green = new Color();
        green.setId(4);
        green.setName("Green");
        var red = new Color();
        red.setId(5);
        red.setName("Red");
        var white = new Color();
        white.setId(6);
        white.setName("White");
        colorList = List.of(black, blue, gray, green, red, white);
    }

    @Test
    void whenFindAllThenGetListOfColors() {
        var result = COLOR_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(colorList).usingRecursiveComparison();
    }

    @Test
    void whenFindByIDThenGetColorOptional() {
        var result = COLOR_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(colorList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = COLOR_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}