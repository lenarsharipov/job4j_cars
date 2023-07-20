package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModelRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final ModelRepository MODEL_REPOSITORY =
            new ModelRepository(CRUD_REPOSITORY);
    private static List<Model> modelList;

    @BeforeAll
    static void init() {
        var duster = new Model();
        duster.setId(1);
        duster.setName("Duster");
        var focus = new Model();
        focus.setId(2);
        focus.setName("Focus");
        var i40 = new Model();
        i40.setId(3);
        i40.setName("i40");
        var v90 = new Model();
        v90.setId(4);
        v90.setName("V90");
        var logan = new Model();
        logan.setId(5);
        logan.setName("Logan");
        var captur = new Model();
        captur.setId(6);
        captur.setName("Captur");

        modelList = List.of(duster, focus, i40, v90, logan, captur);
    }

    @Test
    void whenFindAllThenGetListOfFuelTypes() {
        var result = MODEL_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(modelList);
    }

    @Test
    void whenFindByIDThenGetFuelTypeOptional() {
        var result = MODEL_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(modelList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = MODEL_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}