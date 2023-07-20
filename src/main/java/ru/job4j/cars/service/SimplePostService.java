package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Service
public class SimplePostService implements PostService {

    private PostRepository postRepository;

    @Override
    public Optional<Post> save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean delete(int id) {
        return postRepository.delete(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findTodayPosts() {
        return postRepository.findTodayPosts();
    }

    @Override
    public List<Post> findWithPhoto() {
        return postRepository.findWithPhoto();
    }

    @Override
    public List<Post> findByMake(String make) {
        return postRepository.findByMake(make);
    }

}