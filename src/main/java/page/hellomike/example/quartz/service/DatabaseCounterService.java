package page.hellomike.example.quartz.service;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import page.hellomike.example.quartz.model.Counter;
import page.hellomike.example.quartz.repository.CounterRepository;

import java.util.Collection;
import java.util.UUID;

@Service
@Profile("database")
public class DatabaseCounterService implements CounterService {

    private final CounterRepository counterRepository;

    public DatabaseCounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public Collection<Counter> getAllCounters() {
        return counterRepository.findCounters();
    }

    @Override
    public Counter getCounter(UUID counterId) {
        return counterRepository.findCounterById(counterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Counter createCounter() {
        return counterRepository.createCounter();
    }

    @Override
    public Counter clearCounter(UUID counterId) {
        int changedCount = counterRepository.updateCounterAsClear(counterId);

        if (changedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return counterRepository.findCounterById(counterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void increaseCounter(UUID counterId) {
        int changedCount = counterRepository.updateCounterAsIncrease(counterId);

        if (changedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCounter(UUID counterId) {
        int changedCount = counterRepository.deleteCounter(counterId);

        if (changedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
