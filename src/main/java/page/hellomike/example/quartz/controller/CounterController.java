package page.hellomike.example.quartz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import page.hellomike.example.quartz.model.Counter;
import page.hellomike.example.quartz.model.CounterSchedule;
import page.hellomike.example.quartz.model.CounterScheduleApply;
import page.hellomike.example.quartz.service.CounterDeleteService;
import page.hellomike.example.quartz.service.CounterScheduleService;
import page.hellomike.example.quartz.service.CounterService;

import java.util.Collection;
import java.util.UUID;

@RestController
public class CounterController {

    private final CounterService counterService;

    private final CounterScheduleService counterScheduleService;

    private final CounterDeleteService counterDeleteService;

    public CounterController(
            CounterService counterService,
            CounterScheduleService counterScheduleService,
            CounterDeleteService counterDeleteService
    ) {
        this.counterService = counterService;
        this.counterScheduleService = counterScheduleService;
        this.counterDeleteService = counterDeleteService;
    }

    @GetMapping("/counter")
    public Collection<Counter> getAllCounters() {
        return counterService.getAllCounters();
    }

    @GetMapping("/counter/{counterId}")
    public Counter getCounter(
            @PathVariable("counterId") UUID counterId
    ) {
        return counterService.getCounter(counterId);
    }

    @PostMapping("/counter")
    public Counter createCounter() {
        return counterService.createCounter();
    }

    @PutMapping("/counter/{counterId}")
    public Counter clearCounter(
            @PathVariable("counterId") UUID counterId
    ) {
        return counterService.clearCounter(counterId);
    }

    @DeleteMapping("/counter/{counterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCounter(
            @PathVariable("counterId") UUID counterId
    ) {
        counterDeleteService.deleteCounterWithSchedule(counterId);
    }

    @GetMapping("/counter/{counterId}/schedule")
    public CounterSchedule getCounterSchedule(
            @PathVariable("counterId") UUID counterId
    ) {
        return counterScheduleService.getCounterSchedule(counterId);
    }

    @PutMapping("/counter/{counterId}/schedule")
    public CounterSchedule updateCounterSchedule(
            @PathVariable("counterId") UUID counterId,
            @RequestBody CounterScheduleApply counterScheduleApply
    ) {
        return counterScheduleService.applyCounterSchedule(counterId, counterScheduleApply);
    }

    @DeleteMapping("/counter/{counterId}/schedule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCounterSchedule(
            @PathVariable("counterId") UUID counterId
    ) {
        counterScheduleService.deleteCounterSchedule(counterId);
    }


}
