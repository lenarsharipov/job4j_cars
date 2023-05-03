package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final PostRepository POST_REPOSITORY = new PostRepository(CRUD_REPOSITORY);
    private static final UserRepository USER_REPOSITORY = new UserRepository(CRUD_REPOSITORY);
    private static final EngineRepository ENGINE_REPOSITORY = new EngineRepository(CRUD_REPOSITORY);
    private static final MakeRepository MAKE_REPOSITORY = new MakeRepository(CRUD_REPOSITORY);
    private static final OwnerRepository OWNER_REPOSITORY = new OwnerRepository(CRUD_REPOSITORY);
    private static final CarRepository CAR_REPOSITORY = new CarRepository(CRUD_REPOSITORY);
    private static final PriceHistoryRepository PRICE_HISTORY_REPOSITORY =
            new PriceHistoryRepository(CRUD_REPOSITORY);
    private static final FileRepository FILE_REPOSITORY = new FileRepository(CRUD_REPOSITORY);
    private static Post post;

    @AfterEach
    void clear() {
        var posts = POST_REPOSITORY.findAllOrderById();
        for (var post : posts) {
            POST_REPOSITORY.delete(post.getId());
        }

        var owners = OWNER_REPOSITORY.findAllOrderById();
        for (var owner : owners) {
            OWNER_REPOSITORY.delete(owner.getId());
        }

        var items = PRICE_HISTORY_REPOSITORY.findAllOrderById();
        for (var item : items) {
            PRICE_HISTORY_REPOSITORY.delete(item.getId());
        }
    }

    @BeforeAll
    static void init() {
        var gasoline = ENGINE_REPOSITORY.findAllOrderById().get(0);
        var volvo = MAKE_REPOSITORY.findAll().get(0);
        var current = new Owner();
        current.setName("current owner");
        OWNER_REPOSITORY.create(current);
        var first = new Owner();
        first.setName("first owner");
        OWNER_REPOSITORY.create(first);
        var second = new Owner();
        second.setName("second owner");
        OWNER_REPOSITORY.create(second);
        var owners = new HashSet<>(List.of(first, second, current));

        var car = new Car();
        car.setName("Test car");
        car.setEngine(gasoline);
        car.setOwner(current);
        car.setOwners(owners);
        car.setMake(volvo);
        CAR_REPOSITORY.create(car);

        var priceHistory = new PriceHistory();
        priceHistory.setBefore(100L);
        priceHistory.setAfter(150L);
        PRICE_HISTORY_REPOSITORY.create(priceHistory);
        var priceHistoryList = new HashSet<>(List.of(priceHistory));

        Set<Post> participates = new HashSet<>();

        var defaultPoster = FILE_REPOSITORY.findAll().get(0);

        var user = USER_REPOSITORY.findAllOrderById().get(0);

        post = new Post();
        post.setPriceHistories(priceHistoryList);
        post.setFile(defaultPoster);
        post.setCar(car);
        post.setUser(user);
        post.setDescription("post desc");
        post.setParticipates(participates);
    }

    @Test
    void whenCreatePostThenGetItsOptional() {
        var result = POST_REPOSITORY.create(post);
        assertThat(result).isNotEmpty();
        assertThat(POST_REPOSITORY.findAllOrderById().size()).isEqualTo(1);
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}