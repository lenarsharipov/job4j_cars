package ru.job4j.cars.service;

import ru.job4j.cars.model.BodyStyle;

import java.util.Collection;
import java.util.Optional;

public interface BodyStyleService {
    Collection<BodyStyle> findAll();

    Optional<BodyStyle> findById(int id);
}
