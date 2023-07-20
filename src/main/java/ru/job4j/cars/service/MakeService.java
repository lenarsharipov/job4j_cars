package ru.job4j.cars.service;

import ru.job4j.cars.model.Make;

import java.util.List;
import java.util.Optional;

public interface MakeService {
    List<Make> findAll();

    Optional<Make> findById(int id);
}
