package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BodyStyle;
import ru.job4j.cars.repository.BodyStyleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleBodyStyleService implements BodyStyleService {
    private final BodyStyleRepository bodyStyleRepository;

    @Override
    public Collection<BodyStyle> findAll() {
        return bodyStyleRepository.findAll();
    }

    @Override
    public Optional<BodyStyle> findById(int id) {
        return bodyStyleRepository.findById(id);
    }
}
