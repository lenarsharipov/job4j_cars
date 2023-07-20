package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Door;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DoorRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final DoorRepository DOOR_REPOSITORY = new DoorRepository(CRUD_REPOSITORY);
    private static List<Door> doorList;

    @BeforeAll
    static void init() {
        var two = new Door();
        two.setId(1);
        two.setDoorCount("2 door");
        var three = new Door();
        three.setId(2);
        three.setDoorCount("3 door");
        var four = new Door();
        four.setId(3);
        four.setDoorCount("4 door");
        var five = new Door();
        five.setId(4);
        five.setDoorCount("5 door");
        doorList = new ArrayList<>(List.of(two, three, four, five));
    }

    @Test
    void whenFindAllThenGetListOfDoors() {
        var result = DOOR_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(doorList);
    }

    @Test
    void whenFindByIDThenGetDoorOptional() {
        var result = DOOR_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(doorList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = DOOR_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }


}