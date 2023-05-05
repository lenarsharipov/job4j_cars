package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final CarRepository CAR_REPOSITORY = new CarRepository(CRUD_REPOSITORY);
    private static final EngineRepository ENGINE_REPOSITORY = new EngineRepository(CRUD_REPOSITORY);
    private static final OwnerRepository OWNER_REPOSITORY = new OwnerRepository(CRUD_REPOSITORY);
    private static final MakeRepository MAKE_REPOSITORY = new MakeRepository(CRUD_REPOSITORY);
    private static Set<Owner> owners = new HashSet<>();
    private static Owner current;

    @BeforeAll
    static void init() {
        owners = new HashSet<>();
        current = new Owner();
        current.setName("current owner's name");
        OWNER_REPOSITORY.create(current);
        var first = new Owner();
        first.setName("first owner's name");
        OWNER_REPOSITORY.create(first);
        var second = new Owner();
        second.setName("second owner's name");
        OWNER_REPOSITORY.create(second);
        owners.add(first);
        owners.add(second);
    }

    @AfterAll
    static void clearOwnerRepo() {
        var owners = OWNER_REPOSITORY.findAllOrderById();
        for (var owner : owners) {
            OWNER_REPOSITORY.delete(owner.getId());
        }
    }

    @AfterEach
    void clear() {
        var cars = CAR_REPOSITORY.findAllOrderById();
        for (var car : cars) {
            CAR_REPOSITORY.delete(car.getId());
        }
    }

    /**
     * Create test car.
     * @return Car.
     */
    private Car createCar() {
        var engines = ENGINE_REPOSITORY.findAllOrderById();
        var volvo = MAKE_REPOSITORY.findAll().get(0);
        var car = new Car();
        car.setName("Test car");
        car.setEngine(engines.get(0));
        car.setOwner(current);
        car.setOwners(owners);
        car.setMake(volvo);
        return car;
    }

    /**
     * Save car and get optional of car. New car saved.
     */
    @Test
    void whenSaveCarThenGetOptionalOfSavedCar() {
        assertThat(CAR_REPOSITORY.findAllOrderById()).isEmpty();
        var car = createCar();
        var result = CAR_REPOSITORY.create(car);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(car).usingRecursiveComparison();
        assertThat(CAR_REPOSITORY.findAllOrderById()).isEqualTo(List.of(car));
    }

    /**
     * Update car and get true. Car updated.
     */
    @Test
    void whenUpdateSavedCarThenGetTrueAndCarUpdated() {
        var car = createCar();
        CAR_REPOSITORY.create(car);

        car.setName("UPDATED name");
        assertThat(CAR_REPOSITORY.update(car)).isTrue();
        var id = car.getId();
        var result = CAR_REPOSITORY.findById(id);
        assertThat(result).isNotEmpty();
        assertThat(result.get())
                .isEqualTo(
                new Car(id, "Test car", car.getEngine(), current, owners, car.getMake()))
                .usingRecursiveComparison();
    }

    /**
     * Get false if updated not existing car.
     */
    @Test
    void whenUpdateNotSavedCarThenGetFalse() {
        var car = createCar();

        assertThat(CAR_REPOSITORY.update(car)).isFalse();
        assertThat(CAR_REPOSITORY.findAllOrderById()).isEmpty();
    }

    /**
     * Get car by ID then get found car optional.
     */
    @Test
    void whenFindByIdThenGetOptionalOfCar() {
        assertThat(CAR_REPOSITORY.findAllOrderById()).isEmpty();
        var car = createCar();
        CAR_REPOSITORY.create(car);

        assertThat(CAR_REPOSITORY.findById(car.getId()))
                .isEqualTo(Optional.of(car)).usingRecursiveComparison();
        assertThat(CAR_REPOSITORY.findAllOrderById()).isEqualTo(List.of(car));
    }

    /**
     * Get empty optional if car not found by ID.
     */
    @Test
    void whenCarNotFoundByIdThenGetEmptyOptional() {
        assertThat(CAR_REPOSITORY.findAllOrderById()).isEmpty();
        assertThat(CAR_REPOSITORY.findById(-1)).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}