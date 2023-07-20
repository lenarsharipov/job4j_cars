package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest implements AutoCloseable {
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private static final SessionFactory SESSION_FACTORY = new MetadataSources(REGISTRY)
            .buildMetadata()
            .buildSessionFactory();
    private static final CrudRepository CRUD_REPOSITORY = new CrudRepository(SESSION_FACTORY);
    private static final UserRepository USER_REPOSITORY = new UserRepository(CRUD_REPOSITORY);
    private static User john;
    private static User helen;
    private static User peter;

    @BeforeAll
    static void init() {
        john = new User();
        john.setId(1);
        john.setLogin("John");
        john.setPassword("root");
        helen = new User();
        helen.setId(2);
        helen.setLogin("Helen");
        helen.setPassword("root");
        peter = new User();
        peter.setId(3);
        peter.setLogin("Peter");
        peter.setPassword("root");
    }

    private User createUser(String login, String password) {
        var user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    @Test
    void whenFindAllThenGetListOfPersistedUsers() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
    }

    @Test
    void whenSaveNewUserThenGetOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var testUser = createUser("testUser", "root");
        var result = USER_REPOSITORY.save(testUser);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(testUser).usingRecursiveComparison();
        USER_REPOSITORY.delete(testUser.getLogin());
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
    }

    @Test
    void whenSaveUserWithNotUniqueLoginThenGetEmptyOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var testUser = createUser("John", "root");
        var result = USER_REPOSITORY.save(testUser);
        assertThat(result).isEmpty();
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
    }

    @Test
    void whenUpdateExistingUserThenGetTrue() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var testUser = createUser("testUser", "root");
        USER_REPOSITORY.save(testUser);
        testUser.setPassword("rootUpdated");
        var result = USER_REPOSITORY.update(testUser);
        assertThat(result).isTrue();

        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter, testUser));
        USER_REPOSITORY.delete(testUser.getLogin());
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
    }

    @Test
    void whenUpdateExistingUserThenGetFalse() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var testUser = createUser("testUser", "root");
        var result = USER_REPOSITORY.update(testUser);
        assertThat(result).isFalse();
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
    }

    @Test
    void whenFindByIdThenGetFoundUserOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var result = USER_REPOSITORY.findById(3);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(peter).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingIdThenGetEmptyOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var result = USER_REPOSITORY.findById(-1);
        assertThat(result).isEmpty();
    }

    @Test
    void whenFindByLoginThenGetFoundUserOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var result = USER_REPOSITORY.findByLogin("John");
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(john).usingRecursiveComparison();
    }

    @Test
    void whenFindByNotExistingLoginThenGetEmptyOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var result = USER_REPOSITORY.findByLogin("unknown");
        assertThat(result).isEmpty();
    }

    @Test
    void whenFindByLikeLoginThenGetFoundUserOptional() {
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
        var testUser = createUser("**Peter*", "root2");
        USER_REPOSITORY.save(testUser);
        var result = USER_REPOSITORY.findByLikeLogin("Peter");
        assertThat(result).isEqualTo(List.of(peter, testUser));
        USER_REPOSITORY.delete(testUser.getLogin());
        assertThat(USER_REPOSITORY.findAll()).isEqualTo(List.of(john, helen, peter));
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(REGISTRY);
    }

}