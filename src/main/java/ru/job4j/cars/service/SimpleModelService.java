package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.repository.ModelRepository;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleModelService implements ModelService {
    private final ModelRepository modelRepository;

    @Override
    public Collection<Model> findAll() {
        return modelRepository.findAll();
    }

    @Override
    public Optional<Model> findById(int id) {
        return modelRepository.findById(id);
    }
}
