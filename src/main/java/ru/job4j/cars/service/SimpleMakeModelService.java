package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.MakeModel;
import ru.job4j.cars.repository.MakeModelRepository;

import java.util.List;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleMakeModelService implements MakeModelService {

    private MakeModelRepository makeModelRepository;
    @Override
    public List<MakeModel> findAll() {
        return makeModelRepository.findAll();
    }

    @Override
    public List<MakeModel> findByMakeID(int makeId) {
        return makeModelRepository.findByMakeId(makeId);
    }
}
