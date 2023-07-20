package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Generation;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GenerationRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final GenerationRepository GENERATION_REPOSITORY =
            new GenerationRepository(CRUD_REPOSITORY);
    private static List<Generation> generationList;

    @BeforeAll
    static void init() {
        var gen1 = new Generation();
        gen1.setId(1);
        gen1.setName("1st Generation");
        var gen1Face = new Generation();
        gen1Face.setId(2);
        gen1Face.setName("1st Generation Facelift");
        var gen2 = new Generation();
        gen2.setId(3);
        gen2.setName("2nd Generation");
        var gen2Face = new Generation();
        gen2Face.setId(4);
        gen2Face.setName("2nd Generation Facelift");
        generationList = List.of(gen1, gen1Face, gen2, gen2Face);
    }

    @Test
    void whenFindAllThenGetListOfGenerations() {
        var result = GENERATION_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(generationList).usingRecursiveComparison();
    }

    @Test
    void whenFindByIDThenGetGenerationOptional() {
        var result = GENERATION_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(generationList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = GENERATION_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}