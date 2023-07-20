package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private static final FileRepository FILE_REPOSITORY = new FileRepository(CRUD_REPOSITORY);
    private static final CarRepository CAR_REPOSITORY = new CarRepository(CRUD_REPOSITORY);
    private static final ConditionRepository CONDITION_REPOSITORY =
            new ConditionRepository(CRUD_REPOSITORY);
    private static final YearRepository YEAR_REPOSITORY = new YearRepository(CRUD_REPOSITORY);
    private static final ColorRepository COLOR_REPOSITORY = new ColorRepository(CRUD_REPOSITORY);
    private static final OwnerCountRepository OWNER_COUNT_REPOSITORY =
            new OwnerCountRepository(CRUD_REPOSITORY);
    private static final ModificationRepository MODIFICATION_REPOSITORY =
            new ModificationRepository(CRUD_REPOSITORY);
    private static final PriceHistoryRepository PRICE_HISTORY_REPOSITORY =
            new PriceHistoryRepository(CRUD_REPOSITORY);
    public static final String DELETE_CAR = "DELETE CAR";
    public static final String DELETE_POST = "DELETE POST";
    public static final String DELETE_PRICE_HISTORY = "DELETE PRICE_HISTORY";
    private static Car car;
    private static final User USER = USER_REPOSITORY.findAll().get(0);
    private static List<File> files;
    private static List<PriceHistory> priceHistories;

    @BeforeAll
    static void init() {
        priceHistories = new ArrayList<>();
        var firstPrice = new PriceHistory();
        firstPrice.setBefore(0L);
        firstPrice.setAfter(17_000L);
        PRICE_HISTORY_REPOSITORY.save(firstPrice);
        var lastPrice = new PriceHistory();
        lastPrice.setBefore(firstPrice.getAfter());
        lastPrice.setAfter(16_500L);
        PRICE_HISTORY_REPOSITORY.save(lastPrice);
        priceHistories.add(firstPrice);
        priceHistories.add(lastPrice);

        files = new ArrayList<>();
        var firstPoster = new File();
        firstPoster.setName("duster_1gen_01.png");
        firstPoster.setPath("files/duster_1gen_01.png");
        FILE_REPOSITORY.save(firstPoster);
        var secondPoster = new File();
        secondPoster.setName("duster_1gen_02.png");
        secondPoster.setPath("files/duster_1gen_02.png");
        FILE_REPOSITORY.save(secondPoster);
        files.add(firstPoster);
        files.add(secondPoster);

        var modificationList = MODIFICATION_REPOSITORY.findAll();
        var conditions = CONDITION_REPOSITORY.findAll();
        var years = YEAR_REPOSITORY.findAll();
        var colors = COLOR_REPOSITORY.findAll();
        var ownerCounts = OWNER_COUNT_REPOSITORY.findAll();
        car = new Car();
        car.setName("Test Duster");
        car.setMileage(150_000);
        car.setOwnerName("Test Owner");
        car.setModification(modificationList.get(0));
        car.setYear(years.get(17));
        car.setCondition(conditions.get(0));
        car.setColor(colors.get(0));
        car.setOwnerCount(ownerCounts.get(3));
        CAR_REPOSITORY.save(car);
    }

    private Post createPost() {
        var post = new Post();
        post.setPriceHistories(priceHistories);
        post.setFiles(files);
        post.setHasPhoto(true);
        post.setSold(false);
        post.setCar(car);
        post.setUser(USER);
        post.setDescription("post desc");
        List<User> postFollowers = new ArrayList<>();
        post.setPostFollowers(postFollowers);
        return post;
    }

    @BeforeEach
    @AfterEach
    void clearPostRepo() {
        CRUD_REPOSITORY.run(session ->
                session.createSQLQuery(DELETE_POST).executeUpdate()
        );
    }

    @AfterAll
    static void clear() {
        CRUD_REPOSITORY.run(session -> {
                session.createSQLQuery(DELETE_PRICE_HISTORY).executeUpdate();
                session.createSQLQuery(DELETE_CAR).executeUpdate();
            }
        );
        var files = FILE_REPOSITORY.findAll();
        for (var index = 1; index < files.size(); index++) {
            FILE_REPOSITORY.deleteById(files.get(index).getId());
        }
    }

    @Test
    void whenCreatePostThenGetItsOptional() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        var result = POST_REPOSITORY.save(post);
        assertThat(result).isNotEmpty();
        assertThat(result.get().getPriceHistories().get(0).getAfter()).isEqualTo(17_000L);
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
        var result = POST_REPOSITORY
                .findByMake(
                        post.getCar()
                                .getModification()
                                .getMake()
                                .getName());
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(List.of(post));
    }

    @Test
    void whenFindByMakeNotExistingMakeThenGetEmptyOptional() {
        assertThat(POST_REPOSITORY.findAll()).isEmpty();
        var post = createPost();
        POST_REPOSITORY.save(post);
        var result = POST_REPOSITORY.findByMake("unknown");
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
        postWithoutPhoto.setHasPhoto(false);
        postWithoutPhoto.setFiles(new ArrayList<>());
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