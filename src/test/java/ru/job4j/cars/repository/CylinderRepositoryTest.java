package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Cylinder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CylinderRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final CylinderRepository CYLINDER_REPOSITORY =
            new CylinderRepository(CRUD_REPOSITORY);
    private static List<Cylinder> cylinderList;

    @BeforeAll
    static void init() {
        var one = new Cylinder();
        one.setId(1);
        one.setCount("1 cylinder");
        var two = new Cylinder();
        two.setId(2);
        two.setCount("2 cylinders");
        var three = new Cylinder();
        three.setId(3);
        three.setCount("3 cylinders");
        var four = new Cylinder();
        four.setId(4);
        four.setCount("4 cylinders");
        cylinderList = new ArrayList<>(List.of(one, two, three, four));
    }

    @Test
    void whenFindAllThenGetListOfFuelTypes() {
        var result = CYLINDER_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result.stream().limit(4).collect(Collectors.toList())).isEqualTo(cylinderList);
    }

    @Test
    void whenFindByIDThenGetFuelTypeOptional() {
        var result = CYLINDER_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(cylinderList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = CYLINDER_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}