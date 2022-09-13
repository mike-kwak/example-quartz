package page.hellomike.example.quartz.service;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import page.hellomike.example.quartz.model.Counter;
import page.hellomike.example.quartz.model.ImmutableCounter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Profile("!database")
public class InMemoryCounterService implements CounterService {

    private final ConcurrentMap<UUID, Counter> counterPersistenceMap = new ConcurrentHashMap<>();

    public Collection<Counter> getAllCounters() {
        return counterPersistenceMap.values();
    }

    public Counter getCounter(UUID counterId) {
        return Optional.ofNullable(counterPersistenceMap.get(counterId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Counter createCounter() {
        Counter counter = new ImmutableCounter();
        counterPersistenceMap.put(counter.getId(), counter);
        return counter;
    }

    public Counter clearCounter(UUID counterId) {
        Counter prevCounter = this.getCounter(counterId);
        Counter nextCounter = prevCounter.withClear();
        counterPersistenceMap.put(counterId, nextCounter);
        return nextCounter;
    }

    public void increaseCounter(UUID counterId) {
        counterPersistenceMap.computeIfPresent(counterId, (uuid, counter) -> counter.withValueIncrease());
    }

    public void deleteCounter(UUID counterId) {
        Counter counter = this.getCounter(counterId);
        counterPersistenceMap.remove(counter.getId());
    }

}
