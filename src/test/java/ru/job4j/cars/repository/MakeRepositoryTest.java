package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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

    /**
     * Get list of all persisted makes.
     */
    @Test
    void whenFindAllThenGetPersistedMakesList() {
        var volvo = new Make(1, "VOLVO");
        var lada = new Make(2, "LADA");
        var bmw = new Make(3, "BMW");
        assertThat(MAKE_REPOSITORY.findAll()).isEqualTo(List.of(volvo, lada, bmw));
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}