package page.hellomike.example.quartz.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CounterDeleteService {

    private final CounterService counterService;

    private final CounterScheduleService counterScheduleService;

    public CounterDeleteService(CounterService counterService, CounterScheduleService counterScheduleService) {
        this.counterService = counterService;
        this.counterScheduleService = counterScheduleService;
    }

    public void deleteCounterWithSchedule(UUID counterId) {
        try {
            counterScheduleService.deleteCounterSchedule(counterId);
        }
        catch (ResponseStatusException e) {
            if (e.getStatus() != HttpStatus.NOT_FOUND) {
                throw e;
            }
        }
        counterService.deleteCounter(counterId);
    }

}
