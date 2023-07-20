package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Drivetrain;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DrivetrainRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final DrivetrainRepository DRIVETRAIN_REPOSITORY =
            new DrivetrainRepository(CRUD_REPOSITORY);
    private static List<Drivetrain> drivetrainList;

    @BeforeAll
    static void init() {
        var awd = new Drivetrain();
        awd.setId(1);
        awd.setName("All-wheel drive");
        var fwd = new Drivetrain();
        fwd.setId(2);
        fwd.setName("Front-wheel drive");
        var rwd = new Drivetrain();
        rwd.setId(3);
        rwd.setName("Rear-wheel drive");
        drivetrainList = new ArrayList<>(List.of(awd, fwd, rwd));
    }

    @Test
    void whenFindAllThenGetListOfDrivetrains() {
        var result = DRIVETRAIN_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(drivetrainList);
    }

    @Test
    void whenFindByIDThenGetDrivetrainOptional() {
        var result = DRIVETRAIN_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(drivetrainList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = DRIVETRAIN_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}