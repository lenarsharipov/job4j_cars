package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
class EngineRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final EngineRepository ENGINE_REPOSITORY = new EngineRepository(CRUD_REPOSITORY);
    private static final FuelTypeRepository FUEL_TYPE_REPOSITORY =
            new FuelTypeRepository(CRUD_REPOSITORY);
    private static final HorsePowerRepository HORSE_POWER_REPOSITORY =
            new HorsePowerRepository(CRUD_REPOSITORY);
    private static final EngineCapacityRepository ENGINE_CAPACITY_REPOSITORY =
            new EngineCapacityRepository(CRUD_REPOSITORY);
    private static final CylinderRepository CYLINDER_REPOSITORY =
            new CylinderRepository(CRUD_REPOSITORY);
    private static final List<Engine> ENGINES = new ArrayList<>();

    @BeforeAll
    static void init() {
        var fuelTypes = FUEL_TYPE_REPOSITORY.findAll();
        var horsepowers = HORSE_POWER_REPOSITORY.findAll();
        var engineCapacities = ENGINE_CAPACITY_REPOSITORY.findAll();
        var cylinders = CYLINDER_REPOSITORY.findAll();
        var first = new Engine();
        first.setId(1);
        first.setFuelType(fuelTypes.get(2));
        first.setHorsePower(horsepowers.get(1));
        first.setEngineCapacity(engineCapacities.get(2));
        first.setCylinder(cylinders.get(3));
        ENGINES.add(first);

        var second = new Engine();
        second.setId(2);
        second.setFuelType(fuelTypes.get(2));
        second.setHorsePower(horsepowers.get(5));
        second.setEngineCapacity(engineCapacities.get(0));
        second.setCylinder(cylinders.get(3));
        ENGINES.add(second);

        var third = new Engine();
        third.setId(3);
        third.setFuelType(fuelTypes.get(2));
        third.setHorsePower(horsepowers.get(3));
        third.setEngineCapacity(engineCapacities.get(3));
        third.setCylinder(cylinders.get(3));
        ENGINES.add(third);

        var fourth = new Engine();
        fourth.setId(4);
        fourth.setFuelType(fuelTypes.get(0));
        fourth.setHorsePower(horsepowers.get(0));
        fourth.setEngineCapacity(engineCapacities.get(1));
        fourth.setCylinder(cylinders.get(3));
        ENGINES.add(fourth);

        var fifth = new Engine();
        fifth.setId(5);
        fifth.setFuelType(fuelTypes.get(2));
        fifth.setHorsePower(horsepowers.get(4));
        fifth.setEngineCapacity(engineCapacities.get(3));
        fifth.setCylinder(cylinders.get(3));
        ENGINES.add(fifth);

        var sixth = new Engine();
        sixth.setId(6);
        sixth.setFuelType(fuelTypes.get(0));
        sixth.setHorsePower(horsepowers.get(6));
        sixth.setEngineCapacity(engineCapacities.get(1));
        sixth.setCylinder(cylinders.get(3));
        ENGINES.add(sixth);
    }

    /**
     * Get list of persisted engines.
     */
    @Test
    void whenFindAllThenGetListOfAll() {
        assertThat(ENGINE_REPOSITORY.findAll())
                .isEqualTo(ENGINES)
                .usingRecursiveComparison();
    }

    /**
     * Get optional of engine found by ID.
     */
    @Test
    void whenFindByIdThenGetOptionalOfEngine() {
        var result = ENGINE_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get())
                .isEqualTo(ENGINES.get(0))
                .usingRecursiveComparison();
    }

    /**
     * Get empty optional if not found by ID.
     */
    @Test
    void whenNotFoundByIdThenGetEmptyOptional() {
        var result = ENGINE_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}