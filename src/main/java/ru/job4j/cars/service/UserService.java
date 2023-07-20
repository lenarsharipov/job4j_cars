package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> save(User user);
    boolean update(User user);
    boolean delete(String login);
    List<User> findAll();
    Optional<User> findById(int id);
    List<User> findByLikeLogin(String key);
    Optional<User> findByLogin(String login);

}