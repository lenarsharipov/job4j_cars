package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Optional<Car> save(Car car);
    boolean update(Car car);
    boolean delete(int id);
    List<Car> findAll();
    Optional<Car> findById();
}
