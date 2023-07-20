package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Condition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final ConditionRepository CONDITION_REPOSITORY =
            new ConditionRepository(CRUD_REPOSITORY);
    private static List<Condition> conditionList;

    @BeforeAll
    static void init() {
        var usedCondition = new Condition();
        usedCondition.setId(1);
        usedCondition.setName("Used");
        var newCondition = new Condition();
        newCondition.setId(2);
        newCondition.setName("New");
        conditionList = List.of(usedCondition, newCondition);
    }

    @Test
    void whenFindAllThenGetListOfConditions() {
        var result = CONDITION_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(conditionList).usingRecursiveComparison();
    }

    @Test
    void whenFindByIDThenGetConditionOptional() {
        var result = CONDITION_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(conditionList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = CONDITION_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}