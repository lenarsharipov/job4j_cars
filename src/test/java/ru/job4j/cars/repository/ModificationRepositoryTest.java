package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Modification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ModificationRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final ModificationRepository MODIFICATION_REPOSITORY =
            new ModificationRepository(CRUD_REPOSITORY);
    private static final MakeModelGenerationRepository MAKE_MODEL_GENERATION_REPOSITORY =
            new MakeModelGenerationRepository(CRUD_REPOSITORY);
    private static final TrimRepository TRIM_REPOSITORY = new TrimRepository(CRUD_REPOSITORY);
    private static final BodyStyleRepository BODY_STYLE_REPOSITORY =
            new BodyStyleRepository(CRUD_REPOSITORY);
    private static final EngineRepository ENGINE_REPOSITORY = new EngineRepository(CRUD_REPOSITORY);
    private static final DoorRepository DOOR_REPOSITORY = new DoorRepository(CRUD_REPOSITORY);
    private static final TransmissionRepository TRANSMISSION_REPOSITORY =
            new TransmissionRepository(CRUD_REPOSITORY);
    private static final DrivetrainRepository DRIVETRAIN_REPOSITORY =
            new DrivetrainRepository(CRUD_REPOSITORY);
    private static List<Modification> modificationList;

    @BeforeAll
    static void init() {
        var makeModelGenerationList = MAKE_MODEL_GENERATION_REPOSITORY.findAll();
        var trimList = TRIM_REPOSITORY.findAll();
        var bodyStyleList = BODY_STYLE_REPOSITORY.findAll();
        var engineList = ENGINE_REPOSITORY.findAll();
        var doorList = DOOR_REPOSITORY.findAll();
        var transmissionList = TRANSMISSION_REPOSITORY.findAll();
        var drivetrainList = DRIVETRAIN_REPOSITORY.findAll();

        var first = new Modification();
        first.setId(1);
        first.setMake(makeModelGenerationList.get(0).getMakeModel().getMake());
        first.setModel(makeModelGenerationList.get(0).getMakeModel().getModel());
        first.setGeneration(makeModelGenerationList.get(0).getGeneration());
        first.setStartYear(makeModelGenerationList.get(0).getStartYear());
        first.setEndYear(makeModelGenerationList.get(0).getEndYear());
        first.setTrim(trimList.get(6));
        first.setBodyStyle(bodyStyleList.get(4));
        first.setEngine(engineList.get(5));
        first.setDoor(doorList.get(3));
        first.setTransmission(transmissionList.get(3));
        first.setDrivetrain(drivetrainList.get(1));

        var second = new Modification();
        second.setId(2);
        second.setMake(makeModelGenerationList.get(1).getMakeModel().getMake());
        second.setModel(makeModelGenerationList.get(1).getMakeModel().getModel());
        second.setGeneration(makeModelGenerationList.get(1).getGeneration());
        second.setStartYear(makeModelGenerationList.get(1).getStartYear());
        second.setEndYear(makeModelGenerationList.get(1).getEndYear());
        second.setTrim(trimList.get(5));
        second.setBodyStyle(bodyStyleList.get(4));
        second.setEngine(engineList.get(4));
        second.setDoor(doorList.get(3));
        second.setTransmission(transmissionList.get(1));
        second.setDrivetrain(drivetrainList.get(0));

        var third = new Modification();
        third.setId(3);
        third.setMake(makeModelGenerationList.get(2).getMakeModel().getMake());
        third.setModel(makeModelGenerationList.get(2).getMakeModel().getModel());
        third.setGeneration(makeModelGenerationList.get(2).getGeneration());
        third.setStartYear(makeModelGenerationList.get(2).getStartYear());
        third.setEndYear(makeModelGenerationList.get(2).getEndYear());
        third.setTrim(trimList.get(0));
        third.setBodyStyle(bodyStyleList.get(4));
        third.setEngine(engineList.get(0));
        third.setDoor(doorList.get(3));
        third.setTransmission(transmissionList.get(3));
        third.setDrivetrain(drivetrainList.get(1));
        modificationList = List.of(first, second, third);
    }

    @Test
    void whenFindAllThenGetListOfGenerations() {
        var result = MODIFICATION_REPOSITORY.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(modificationList).usingRecursiveComparison();
    }

    @Test
    void whenFindByIDThenGetGenerationOptional() {
        var result = MODIFICATION_REPOSITORY.findById(1);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(modificationList.get(0)).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIDThenGetEmptyOptional() {
        var result = MODIFICATION_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}