package page.hellomike.example.quartz.service;

import org.springframework.stereotype.Service;
import page.hellomike.example.quartz.model.Counter;

import java.util.Collection;
import java.util.UUID;

@Service
public interface CounterService {

    Collection<Counter> getAllCounters();

    Counter getCounter(UUID counterId);

    Counter createCounter();

    Counter clearCounter(UUID counterId);

    void increaseCounter(UUID counterId);

    void deleteCounter(UUID counterId);

}
