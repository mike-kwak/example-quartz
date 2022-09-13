package page.hellomike.example.quartz.model;

import java.time.ZonedDateTime;

public class CounterSchedule {

    private final ZonedDateTime startAt;

    private final ZonedDateTime nextFireAt;

    private final Counter counter;

    public CounterSchedule(ZonedDateTime startAt, ZonedDateTime nextFireAt, Counter counter) {
        this.startAt = startAt;
        this.nextFireAt = nextFireAt;
        this.counter = counter;
    }

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public ZonedDateTime getNextFireAt() {
        return nextFireAt;
    }

    public Counter getCounter() {
        return counter;
    }
}
