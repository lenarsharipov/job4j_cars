package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Trim;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TrimRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final TrimRepository TRIM_REPOSITORY =
            new TrimRepository(CRUD_REPOSITORY);
    private static List<Trim> trimList;

    @BeforeAll
    static void init() {
        var access = new Trim();
        access.setId(1);
        access.setName("Access");
        var editionOne = new Trim();
        editionOne.setId(2);
        editionOne.setName("Edition One");
        var life = new Trim();
        life.setId(3);
        life.setName("Life");
        var drive = new Trim();
        drive.setId(4);
        drive.setName("Drive");
        var style = new Trim();
        style.setId(5);
        style.setName("Style");
        var expression = new Trim();
        expression.setId(6);
        expression.setName("Expression");
        var privilege = new Trim();
        privilege.setId(7);
        privilege.setName("Privilege");
        trimList = List.of(access, editionOne, life, drive, style, expression, privilege);
    }

    @Test
    void whenFindAllThenGetListOfTrims() {
        var result = TRIM_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(trimList).usingRecursiveComparison();
    }

    @Test
    void whenFindByIDThenGetTrimOptional() {
        var result = TRIM_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(trimList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = TRIM_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}