package page.hellomike.example.quartz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public record CounterScheduleApply(ZonedDateTime startAt, String cronExpression) {

    public CounterScheduleApply(ZonedDateTime startAt, String cronExpression) {
        this.startAt = Objects.requireNonNullElseGet(startAt, ZonedDateTime::now);
        this.cronExpression = cronExpression;
    }

    @JsonIgnore
    public Date convertStartAt() {
        return Date.from(this.startAt.toInstant());
    }

}
