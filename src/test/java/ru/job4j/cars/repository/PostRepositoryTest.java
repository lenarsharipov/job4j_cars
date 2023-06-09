package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.util.TestQuery;

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
    static void initCar() {
        var gasoline = ENGINE_REPOSITORY.findAll().get(0);
        var volvo = MAKE_REPOSITORY.findAll().get(0);
        var current = new Owner();
        current.setName("current owner");
        OWNER_REPOSITORY.save(current);
        car = new Car();
        car.setName("Test car");
        car.setEngine(gasoline);
        car.setOwner(current);
        car.setOwners(new HashSet<>());
        car.setMake(volvo);
        CAR_REPOSITORY.save(car);
    }

    private Post createPost() {
        var post = new Post();
        var priceHistoryList = new ArrayList<PriceHistory>();
        post.setPriceHistories(priceHistoryList);
        var defaultPoster = FILE_REPOSITORY.findAll().get(0);
        post.setFile(defaultPoster);
        post.setCar(car);
        var user = USER_REPOSITORY.findAll().get(0);
        post.setUser(user);
        post.setDescription("post desc");
        List<Post> participates = new ArrayList<>();
        post.setParticipates(participates);
        return post;
    }

    @BeforeEach
    @AfterEach
    void clearPostRepo() {
        CRUD_REPOSITORY.run(session ->
                session.createSQLQuery(TestQuery.DELETE_POST).executeUpdate()
        );
    }

    @AfterAll
    static void clear() {
        CRUD_REPOSITORY.run(session -> {
                session.createSQLQuery(TestQuery.DELETE_CAR).executeUpdate();
                session.createSQLQuery(TestQuery.DELETE_OWNERS).executeUpdate();
            }
        );
    }

    @Test
    void whenCreatePostThenGetItsOptional() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        var result = POST_REPOSITORY.save(post);
        assertThat(result).isNotEmpty();
        var posts = POST_REPOSITORY.findAll();
        assertThat(posts).isEqualTo(List.of(post));
    }

    @Test
    void whenDeletePersistedPostThenTrue() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.save(post);
        var result = POST_REPOSITORY.delete(post.getId());
        assertThat(result).isTrue();
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
    }

    @Test
    void whenDeleteNotPersistedPostThenFalse() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.save(post);
        var result = POST_REPOSITORY.delete(-1);
        assertThat(result).isFalse();
        assertThat(POST_REPOSITORY.findAll()).isEqualTo(List.of(post));
    }

    @Test
    void whenUpdateCreatedPostThenGetTrue() {
        var post = createPost();
        POST_REPOSITORY.save(post);
        post.setDescription("desc UPDATED");
        var result = POST_REPOSITORY.update(post);
        assertThat(result).isTrue();
        assertThat(POST_REPOSITORY.findAll()).isEqualTo(List.of(post));
    }

    @Test
    void whenUpdateNotExistingPostThenGetFalse() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        var result = POST_REPOSITORY.update(post);
        assertThat(result).isFalse();
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
    }

    @Test
    void whenFindByMakeExistingPostThenGetOptionalOfPost() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.save(post);
        var result = POST_REPOSITORY.findByMake(post.getCar().getMake().getName());
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(List.of(post));
    }

    @Test
    void whenFindByMakeNotExistingMakeThenGetEmptyOptional() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.save(post);
        var result = POST_REPOSITORY.findByMake("lada");
        assertThat(result).isEmpty();
    }

    @Test
    void whenFindByIdPersistedPostThenGetOptionalOfPost() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.save(post);
        var result = POST_REPOSITORY.findById(post.getId());
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(post);
    }

    @Test
    void whenFindByTodayThenGetListOfTodayPosts() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var todayPost1 = createPost();
        var todayPost2 = createPost();
        POST_REPOSITORY.save(todayPost1);
        POST_REPOSITORY.save(todayPost2);
        var oldPost = createPost();
        var oldDate = LocalDateTime.now().withYear(2000);
        oldPost.setCreated(oldDate);
        POST_REPOSITORY.save(oldPost);
        assertThat(POST_REPOSITORY.findAll())
                .isEqualTo(List.of(todayPost1, todayPost2, oldPost));
        var result = POST_REPOSITORY.findTodayPosts();
        assertThat(result).isEqualTo(List.of(todayPost1, todayPost2));
    }

    @Test
    void whenFindByTodayThenGetEmptyListOfTodayPosts() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var oldPost = createPost();
        var oldDate = LocalDateTime.now().withYear(2000);
        oldPost.setCreated(oldDate);
        POST_REPOSITORY.save(oldPost);
        assertThat(POST_REPOSITORY.findAll())
                .isEqualTo(List.of(oldPost));
        var result = POST_REPOSITORY.findTodayPosts();
        assertThat(result).isEmpty();
    }

    @Test
    void whenGetPostsWithPhotoThenGetListOfPosts() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var postWithPhoto = createPost();
        var postWithoutPhoto = createPost();
        postWithoutPhoto.setFile(null);
        POST_REPOSITORY.save(postWithPhoto);
        POST_REPOSITORY.save(postWithoutPhoto);
        assertThat(POST_REPOSITORY.findWithPhoto())
                .isEqualTo(List.of(postWithPhoto));
    }

    @Override
    public void close()  {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }
}