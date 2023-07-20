package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Make;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MakeRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final MakeRepository MAKE_REPOSITORY = new MakeRepository(CRUD_REPOSITORY);
    private static final ModelRepository MODEL_REPOSITORY = new ModelRepository(CRUD_REPOSITORY);
    private static List<Make> makeList;

    @BeforeAll
    static void init() {
        var modelList = MODEL_REPOSITORY.findAll();
        var ford = new Make();
        ford.setId(1);
        ford.setName("Ford");
        ford.setModels(List.of(modelList.get(1)));
        var hyundai = new Make();
        hyundai.setId(2);
        hyundai.setName("Hyundai");
        hyundai.setModels(List.of(modelList.get(2)));
        var renault = new Make();
        renault.setId(3);
        renault.setModels(List.of(modelList.get(0)));
        renault.setName("Renault");
        var volvo = new Make();
        volvo.setId(4);
        volvo.setName("Volvo");
        volvo.setModels(List.of(modelList.get(3)));
        makeList = List.of(ford, hyundai, renault, volvo);
    }

    @Test
    void whenFindAllThenGetListOfMakes() {
        var result = MAKE_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(makeList);
    }

    @Test
    void whenFindByIDThenGetMakeOptional() {
        var result = MAKE_REPOSITORY.findById(1);
        System.out.println(result);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(makeList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = MAKE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}