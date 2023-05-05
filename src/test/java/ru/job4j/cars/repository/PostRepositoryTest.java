package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    private static final FileRepository FILE_REPOSITORY = new FileRepository(CRUD_REPOSITORY);
    private static Car car;

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

        car = new Car();
        car.setName("Test car");
        car.setEngine(gasoline);
        car.setOwner(current);
        car.setOwners(owners);
        car.setMake(volvo);
        CAR_REPOSITORY.create(car);
    }

    private Post createPost() {
        var post = new Post();
        var priceHistoryList = new ArrayList<PriceHistory>();
        post.setPriceHistories(priceHistoryList);
        var defaultPoster = FILE_REPOSITORY.findAll().get(0);
        post.setFile(defaultPoster);
        post.setCar(car);
        var user = USER_REPOSITORY.findAllOrderById().get(0);
        post.setUser(user);
        post.setDescription("post desc");
        List<Post> participates = new ArrayList<>();
        post.setParticipates(participates);
        return post;
    }

    @BeforeEach
    void clearPostRepo() {
        var posts = POST_REPOSITORY.findAllOrderById();
        for (var post : posts) {
            POST_REPOSITORY.delete(post.getId());
        }
    }

    @AfterAll
    static void clear() {
        var owners = OWNER_REPOSITORY.findAllOrderById();
        for (var owner : owners) {
            OWNER_REPOSITORY.delete(owner.getId());
        }

        var cars = CAR_REPOSITORY.findAllOrderById();
        for (var car : cars) {
            CAR_REPOSITORY.delete(car.getId());
        }

    }

    @Test
    void whenCreatePostThenGetItsOptional() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        var result = POST_REPOSITORY.create(post);
        assertThat(result).isNotEmpty();
        var posts = POST_REPOSITORY.findAllOrderById();
        assertThat(posts).isEqualTo(List.of(post));
    }

    @Test
    void whenDeletePersistedPostThenTrue() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.create(post);
        var result = POST_REPOSITORY.delete(post.getId());
        assertThat(result).isTrue();
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
    }

    @Test
    void whenDeleteNotPersistedPostThenFalse() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.create(post);
        var result = POST_REPOSITORY.delete(-1);
        assertThat(result).isFalse();
        assertThat(POST_REPOSITORY.findAllOrderById()).isEqualTo(List.of(post));
    }

    @Test
    void whenUpdateCreatedPostThenGetTrue() {
        var post = createPost();
        POST_REPOSITORY.create(post);
        post.setDescription("desc UPDATED");
        var result = POST_REPOSITORY.update(post);
        assertThat(result).isTrue();
        assertThat(POST_REPOSITORY.findAllOrderById()).isEqualTo(List.of(post));
    }

    @Test
    void whenUpdateNotExistingPostThenGetFalse() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        var result = POST_REPOSITORY.update(post);
        assertThat(result).isFalse();
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
    }

    @Test
    void whenFindByMakeExistingPostThenGetOptionalOfPost() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.create(post);
        var result = POST_REPOSITORY.findByMake(post.getCar().getMake().getName());
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(List.of(post));
    }

    @Test
    void whenFindByMakeNotExistingMakeThenGetEmptyOptional() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.create(post);
        var result = POST_REPOSITORY.findByMake("lada");
        assertThat(result).isEmpty();
    }

    @Test
    void whenFindByIdPersistedPostThenGetOptionalOfPost() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.create(post);
        var result = POST_REPOSITORY.findById(post.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(post);
    }

    @Test
    void whenFindByTodayThenGetListOfTodayPosts() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var todayPost1 = createPost();
        var todayPost2 = createPost();
        POST_REPOSITORY.create(todayPost1);
        POST_REPOSITORY.create(todayPost2);
        var oldPost = createPost();
        var oldDate = LocalDateTime.now().withYear(2000);
        oldPost.setCreated(oldDate);
        POST_REPOSITORY.create(oldPost);
        assertThat(POST_REPOSITORY.findAllOrderById())
                .isEqualTo(List.of(todayPost1, todayPost2, oldPost));
        var result = POST_REPOSITORY.findTodayPosts();
        assertThat(result).isEqualTo(List.of(todayPost1, todayPost2));
    }

    @Test
    void whenFindByTodayThenGetEmptyListOfTodayPosts() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var oldPost = createPost();
        var oldDate = LocalDateTime.now().withYear(2000);
        oldPost.setCreated(oldDate);
        POST_REPOSITORY.create(oldPost);
        assertThat(POST_REPOSITORY.findAllOrderById())
                .isEqualTo(List.of(oldPost));
        var result = POST_REPOSITORY.findTodayPosts();
        assertThat(result).isEmpty();
    }

    @Test
    void whenGetPostsWithPhotoThenGetListOfPosts() {
        assertThat(POST_REPOSITORY.findAllOrderById()).isEmpty();
        var postWithPhoto = createPost();
        var postWithoutPhoto = createPost();
        postWithoutPhoto.setFile(null);
        POST_REPOSITORY.create(postWithPhoto);
        POST_REPOSITORY.create(postWithoutPhoto);
        assertThat(POST_REPOSITORY.findWithPhoto())
                .isEqualTo(List.of(postWithPhoto));
    }

    @Override
    public void close()  {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}