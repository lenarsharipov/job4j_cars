package ru.job4j.cars.service;

import ru.job4j.cars.model.MakeModel;

import java.util.List;

public interface MakeModelService {
    List<MakeModel> findAll();
    List<MakeModel> findByMakeID(int makeId);

}
