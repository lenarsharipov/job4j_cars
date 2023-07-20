package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
@Service
public class SimplePriceHistoryService implements PriceHistoryService {

    private PriceHistoryRepository priceHistoryRepository;

    @Override
    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    @Override
    public boolean update(PriceHistory priceHistory) {
        return priceHistoryRepository.update(priceHistory);
    }

    @Override
    public boolean delete(int id) {
        return priceHistoryRepository.delete(id);
    }

    @Override
    public List<PriceHistory> findAll() {
        return priceHistoryRepository.findAll();
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return priceHistoryRepository.findById(id);
    }

}
