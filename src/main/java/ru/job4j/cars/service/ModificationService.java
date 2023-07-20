package ru.job4j.cars.service;

import ru.job4j.cars.model.Modification;

import java.util.Collection;

public interface ModificationService {
    Collection<Modification> findAll();
}
