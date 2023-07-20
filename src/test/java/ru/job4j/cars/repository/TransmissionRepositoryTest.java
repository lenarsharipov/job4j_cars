package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Transmission;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TransmissionRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final TransmissionRepository TRANSMISSION_REPOSITORY =
            new TransmissionRepository(CRUD_REPOSITORY);
    private static List<Transmission> transmissionList;

    @BeforeAll
    static void init() {
        var diesel = new Transmission();
        diesel.setId(1);
        diesel.setType("Automanual");
        var electric = new Transmission();
        electric.setId(2);
        electric.setType("Automatic");
        var gasoline = new Transmission();
        gasoline.setId(3);
        gasoline.setType("CVT");
        var hybrid = new Transmission();
        hybrid.setId(4);
        hybrid.setType("Manual");
        transmissionList = new ArrayList<>(List.of(diesel, electric, gasoline, hybrid));
    }

    @Test
    void whenFindAllThenGetListOfTransmissions() {
        var result = TRANSMISSION_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(transmissionList);
    }

    @Test
    void whenFindByIDThenGetTransmissionOptional() {
        var result = TRANSMISSION_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(transmissionList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = TRANSMISSION_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}