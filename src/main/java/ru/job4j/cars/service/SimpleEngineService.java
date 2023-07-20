package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Service
public class SimpleEngineService implements EngineService {

    private EngineRepository engineRepository;

    @Override
    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

}
