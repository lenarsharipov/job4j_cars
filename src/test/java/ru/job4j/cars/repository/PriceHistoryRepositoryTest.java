package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
class PriceHistoryRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final PriceHistoryRepository PRICE_HISTORY_REPOSITORY =
            new PriceHistoryRepository(CRUD_REPOSITORY);
    public static final String DELETE_PRICE_HISTORY = "DELETE PRICE_HISTORY";

    @BeforeEach
    void clearTable() {
        CRUD_REPOSITORY.run(
                session -> session
                        .createSQLQuery(DELETE_PRICE_HISTORY)
                        .executeUpdate());
    }

    /**
     * Save item and get optional of it.
     */
    @Test
    void whenCreateThenGetOptionalOfCreated() {
        var item = new PriceHistory();

        item.setBefore(100L);
        item.setAfter(150L);
        var result = PRICE_HISTORY_REPOSITORY.save(item);
        assertThat(result).isNotEmpty();
        assertThat(PRICE_HISTORY_REPOSITORY.findAll()).isEqualTo(List.of(item));
    }

    /**
     * Update saved item and get true.
     */
    @Test
    void whenUpdateSavedItemThenGetTrue() {
        var item = new PriceHistory();
        item.setBefore(100L);
        item.setAfter(150L);
        PRICE_HISTORY_REPOSITORY.save(item);
        item.setAfter(75L);
        item.setAfter(150L);
        var result = PRICE_HISTORY_REPOSITORY.update(item);
        assertThat(result).isTrue();
        assertThat(PRICE_HISTORY_REPOSITORY.findById(item.getId()))
                .isEqualTo(Optional.of(item))
                .usingRecursiveComparison();
    }

    /**
     * Get false if update not existing item.
     */
    @Test
    void whenUpdateNotExistingItemThenGetFalse() {
        var item = new PriceHistory();
        item.setBefore(100L);
        item.setAfter(150L);
        var result = PRICE_HISTORY_REPOSITORY.update(item);
        assertThat(result).isFalse();
        assertThat(PRICE_HISTORY_REPOSITORY.findAll()).isEmpty();
    }

    /**
     * Delete item by id and get true.
     */
    @Test
    void whenDeleteThenDeletedAndGetTrue() {
        var item = new PriceHistory();
        item.setBefore(100L);
        item.setAfter(150L);
        PRICE_HISTORY_REPOSITORY.save(item);
        assertThat(PRICE_HISTORY_REPOSITORY.delete(item.getId())).isTrue();
        assertThat(PRICE_HISTORY_REPOSITORY.findAll()).isEmpty();
    }

    /**
     * Delete item by incorrect id and get false.
     */
    @Test
    void whenDeleteByIncorrectIdThenGetFalse() {
        var item = new PriceHistory();
        item.setBefore(100L);
        item.setAfter(150L);
        PRICE_HISTORY_REPOSITORY.save(item);
        assertThat(PRICE_HISTORY_REPOSITORY.delete(-1)).isFalse();
        assertThat(PRICE_HISTORY_REPOSITORY.findAll()).isEqualTo(List.of(item));
    }

    /**
     * Get optional of item found by id.
     */
    @Test
    void whenFindByIdThenGetOptionalOfFoundItem() {
        var item = new PriceHistory();
        item.setBefore(100L);
        item.setAfter(150L);
        PRICE_HISTORY_REPOSITORY.save(item);
        var result = PRICE_HISTORY_REPOSITORY.findById(item.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(item).usingRecursiveComparison();
    }

    /**
     * Get empty optional if item not found by id.
     */
    @Test
    void whenItemNotFoundByIdThenGetEmptyOptional() {
        var result = PRICE_HISTORY_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}