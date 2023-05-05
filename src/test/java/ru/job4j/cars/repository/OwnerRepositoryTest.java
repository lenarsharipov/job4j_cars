package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.util.TestQuery;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final OwnerRepository OWNER_REPOSITORY = new OwnerRepository(CRUD_REPOSITORY);

    @AfterEach
    @BeforeEach
    void clearTable() {
        CRUD_REPOSITORY.run(
                session -> session
                        .createSQLQuery(TestQuery.DELETE_OWNERS)
                        .executeUpdate());
    }

    /**
     * Save new Owner ang get optional of saved owner.
     */
    @Test
    void whenSaveOwnerThenGetOptionalOfOwner() {
        assertThat(OWNER_REPOSITORY.findAllOrderById()).isEmpty();
        var owner = new Owner();
        owner.setName("owner name");
        var result = OWNER_REPOSITORY.create(owner);
        assertThat(result).isNotEmpty();
        assertThat(OWNER_REPOSITORY.findAllOrderById()).isEqualTo(List.of(result.get()));
    }

    /**
     * Delete Owner then get true.
     */
    @Test
    void whenDeleteThenGetTrue() {
        var owner = new Owner();
        owner.setName("owner name");
        OWNER_REPOSITORY.create(owner);
        assertThat(OWNER_REPOSITORY.delete(owner.getId())).isTrue();
        assertThat(OWNER_REPOSITORY.findAllOrderById()).isEmpty();
    }

    /**
     * Delete Owner by incorrect ID then get false.
     */
    @Test
    void whenDeleteByIncorrectIdThenGetFalse() {
        assertThat(OWNER_REPOSITORY.delete(-1)).isFalse();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}