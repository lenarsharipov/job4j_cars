package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Make;
import ru.job4j.cars.repository.MakeRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Service
public class SimpleMakeService implements MakeService {

    private MakeRepository makeRepository;

    @Override
    public List<Make> findAll() {
        return makeRepository.findAll();
    }

    @Override
    public Optional<Make> findById(int id) {
        return makeRepository.findById(id);
    }

}