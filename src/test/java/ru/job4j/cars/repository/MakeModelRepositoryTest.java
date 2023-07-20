package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.MakeModel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MakeModelRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
//    private static final MakeModelRepository MAKE_MODEL_REPOSITORY =
//            new MakeModelRepository(CRUD_REPOSITORY);
//    private static final MakeRepository MAKE_REPOSITORY = new MakeRepository(CRUD_REPOSITORY);
//    private static final ModelRepository MODEL_REPOSITORY = new ModelRepository(CRUD_REPOSITORY);
//    private static List<MakeModel> makeModelList;
//
//    @BeforeAll
//    static void init() {
//        var modelList = MODEL_REPOSITORY.findAll();
//        var makeList = MAKE_REPOSITORY.findAll();
//        var first = new MakeModel();
//        first.setId(1);
//        first.setMake(makeList.get(0));
//        first.setModel(modelList.get(1));
//        var second = new MakeModel();
//        second.setId(2);
//        second.setMake(makeList.get(1));
//        second.setModel(modelList.get(2));
//        var third = new MakeModel();
//        third.setId(3);
//        third.setMake(makeList.get(2));
//        third.setModel(modelList.get(0));
//        var fourth = new MakeModel();
//        fourth.setId(4);
//        fourth.setMake(makeList.get(3));
//        fourth.setModel(modelList.get(3));
//        makeModelList = List.of(first, second, third, fourth);
//    }
//
//    @Test
//    void whenFindAllThenGetListOfMakeModels() {
//        var result = MAKE_MODEL_REPOSITORY.findAll();
//        assertThat(result).isNotEmpty();
//        assertThat(result).isEqualTo(makeModelList).usingRecursiveComparison();
//        assertThat(result.get(0).getMake().getName()).isEqualTo("Ford");
//        assertThat(result.get(0).getModel().getName()).isEqualTo("Focus");
//    }
//
//    @Test
//    void whenFindByIDThenGetMakeModelOptional() {
//        var result = MAKE_MODEL_REPOSITORY.findById(1);
//        assertThat(result).isNotEmpty();
//        assertThat(result.get()).isEqualTo(makeModelList.get(0)).usingRecursiveComparison();
//    }
//
//    @Test
//    void whenFindByNotExistingIDThenGetEmptyOptional() {
//        var result = MAKE_MODEL_REPOSITORY.findById(-1);
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    void whenFindByMakeIdThenGetEmptyOptional() {
//        var result = MAKE_MODEL_REPOSITORY.findById(-1);
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    void whenFindModelsByMakeIdThenGetList() {
//        var result = MAKE_MODEL_REPOSITORY.findByMakeId(3);
//        System.out.println(result);
//    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}