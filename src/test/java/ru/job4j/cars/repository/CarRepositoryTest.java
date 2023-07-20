package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final CarRepository CAR_REPOSITORY = new CarRepository(CRUD_REPOSITORY);
    private static final ConditionRepository CONDITION_REPOSITORY =
            new ConditionRepository(CRUD_REPOSITORY);
    private static final YearRepository YEAR_REPOSITORY = new YearRepository(CRUD_REPOSITORY);
    private static final ColorRepository COLOR_REPOSITORY = new ColorRepository(CRUD_REPOSITORY);
    private static final OwnerCountRepository OWNER_COUNT_REPOSITORY =
            new OwnerCountRepository(CRUD_REPOSITORY);
    private static final ModificationRepository MODIFICATION_REPOSITORY =
            new ModificationRepository(CRUD_REPOSITORY);
    public static final String DELETE_CAR = "DELETE CAR";

    @BeforeEach
    @AfterEach
    void clearTable() {
        CRUD_REPOSITORY.run(
                session -> session
                        .createSQLQuery(DELETE_CAR)
                        .executeUpdate());
    }

    /**
     * Create test car.
     * @return Car.
     */
    private Car createCar() {
        var modificationList = MODIFICATION_REPOSITORY.findAll();
        var dusterGen1 = modificationList.get(0);
        var conditions = CONDITION_REPOSITORY.findAll();
        var years = YEAR_REPOSITORY.findAll();
        var colors = COLOR_REPOSITORY.findAll();
        var ownerCounts = OWNER_COUNT_REPOSITORY.findAll();
        var car = new Car();
        car.setName("Test Duster");
        car.setMileage(150_000);
        car.setOwnerName("Test Owner");
        car.setModification(modificationList.get(0));
        car.setYear(years.get(17));
        car.setCondition(conditions.get(0));
        car.setColor(colors.get(0));
        car.setOwnerCount(ownerCounts.get(3));
        return car;
    }

    /**
     * Save car and get optional of car. New car saved.
     */
    @Test
    void whenSaveCarThenGetOptionalOfSavedCar() {
        assertThat(CAR_REPOSITORY.findAll()).isEmpty();
        var car = createCar();
        var result = CAR_REPOSITORY.save(car);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(car).usingRecursiveComparison();
        assertThat(CAR_REPOSITORY.findAll()).isEqualTo(List.of(car));
    }

    /**
     * Update car and get true. Car updated.
     */
    @Test
    void whenUpdateSavedCarThenGetTrueAndCarUpdated() {
        var car = createCar();
        CAR_REPOSITORY.save(car);

        car.setName("UPDATED name");
        assertThat(CAR_REPOSITORY.update(car)).isTrue();
        var id = car.getId();
        var result = CAR_REPOSITORY.findById(id);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(car).usingRecursiveComparison();
    }

    /**
     * Get false if updated not existing car.
     */
    @Test
    void whenUpdateNotSavedCarThenGetFalse() {
        var car = createCar();

        assertThat(CAR_REPOSITORY.update(car)).isFalse();
        assertThat(CAR_REPOSITORY.findAll()).isEmpty();
    }

    /**
     * Get car by ID then get found car optional.
     */
    @Test
    void whenFindByIdThenGetOptionalOfCar() {
        assertThat(CAR_REPOSITORY.findAll()).isEmpty();
        var car = createCar();
        CAR_REPOSITORY.save(car);

        assertThat(CAR_REPOSITORY.findById(car.getId()))
                .isEqualTo(Optional.of(car)).usingRecursiveComparison();
        assertThat(CAR_REPOSITORY.findAll()).isEqualTo(List.of(car));
    }

    /**
     * Get empty optional if car not found by ID.
     */
    @Test
    void whenCarNotFoundByIdThenGetEmptyOptional() {
        assertThat(CAR_REPOSITORY.findAll()).isEmpty();
        assertThat(CAR_REPOSITORY.findById(-1)).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}