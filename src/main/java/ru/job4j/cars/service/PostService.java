package ru.job4j.cars.service;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> save(Post post);
    boolean update(Post post);
    boolean delete(int id);
    List<Post> findAll();
    Optional<Post> findById(int id);
    List<Post> findTodayPosts();
    List<Post> findWithPhoto();
    List<Post> findByMake(String make);

}