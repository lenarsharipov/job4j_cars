package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.MakeModelGeneration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MakeModelGenerationRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
//    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
//            .buildMetadata()
//            .buildSessionFactory();
//    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
//    private static final MakeModelGenerationRepository MAKE_MODEL_GENERATION_REPOSITORY =
//            new MakeModelGenerationRepository(CRUD_REPOSITORY);
//    private static final MakeModelRepository MAKE_MODEL_REPOSITORY =
//            new MakeModelRepository(CRUD_REPOSITORY);
//    private static final GenerationRepository GENERATION_REPOSITORY =
//            new GenerationRepository(CRUD_REPOSITORY);
//    private static final YearRepository YEAR_REPOSITORY = new YearRepository(CRUD_REPOSITORY);
//    private static List<MakeModelGeneration> makeModelGenerationList;
//
//    @BeforeAll
//    static void init() {
//        var makeModelList = MAKE_MODEL_REPOSITORY.findAll();
//        var generationList = GENERATION_REPOSITORY.findAll();
//        var yearList = YEAR_REPOSITORY.findAll();
//        var first = new MakeModelGeneration();
//        first.setId(1);
//        first.setMakeModel(makeModelList.get(2));
//        first.setGeneration(generationList.get(0));
//        first.setStartYear(yearList.get(13));
//        first.setEndYear(yearList.get(14));
//        var second = new MakeModelGeneration();
//        second.setId(2);
//        second.setMakeModel(makeModelList.get(2));
//        second.setGeneration(generationList.get(1));
//        second.setStartYear(yearList.get(14));
//        second.setEndYear(yearList.get(24));
//        var third = new MakeModelGeneration();
//        third.setId(3);
//        third.setMakeModel(makeModelList.get(2));
//        third.setGeneration(generationList.get(2));
//        third.setStartYear(yearList.get(23));
//        third.setEndYear(null);
//        makeModelGenerationList = List.of(first, second, third);
//    }
//
//    @Test
//    void whenFindAllThenGetListOfMakeModelGenerations() {
//        var result = MAKE_MODEL_GENERATION_REPOSITORY.findAll();
//        assertThat(result).isNotEmpty();
//        assertThat(result)
//                .isEqualTo(makeModelGenerationList)
//                .usingRecursiveComparison();
//    }
//
//    @Test
//    void whenFindByIDThenGetMakeModelGenerationOptional() {
//        var result = MAKE_MODEL_GENERATION_REPOSITORY.findById(1);
//        assertThat(result).isNotEmpty();
//        assertThat(result.get())
//                .isEqualTo(makeModelGenerationList.get(0))
//                .usingRecursiveComparison();
//    }
//
//    @Test
//    void whenFindByNotExistingIDThenGetEmptyOptional() {
//        var result = MAKE_MODEL_GENERATION_REPOSITORY.findById(-1);
//        assertThat(result).isEmpty();
//    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}