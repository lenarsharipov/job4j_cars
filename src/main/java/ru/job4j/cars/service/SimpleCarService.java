package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Service
public class SimpleCarService implements CarService {

    private CarRepository carRepository;

    @Override
    public Optional<Car> save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public boolean delete(int id) {
        return carRepository.delete(id);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById() {
        return Optional.empty();
    }
}
