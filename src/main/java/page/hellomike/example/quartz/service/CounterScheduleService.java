package page.hellomike.example.quartz.service;

import org.quartz.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import page.hellomike.example.quartz.model.Counter;
import page.hellomike.example.quartz.model.CounterSchedule;
import page.hellomike.example.quartz.model.CounterScheduleApply;
import page.hellomike.example.quartz.schedule.CounterIncreaseJob;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.UUID;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Service
public class CounterScheduleService {

    public static final String JOB_GROUP = "counter_schedule";

    private final CounterService counterService;

    private final Scheduler scheduler;

    public CounterScheduleService(CounterService counterService, Scheduler scheduler) {
        this.counterService = counterService;
        this.scheduler = scheduler;
    }

    public CounterSchedule applyCounterSchedule(UUID counterId, CounterScheduleApply counterScheduleApply) {
        try {
            CronExpression cronExpression = new CronExpression(counterScheduleApply.cronExpression());

            Counter counter = counterService.getCounter(counterId);

            String counterIdStr = counter.getId().toString();

            JobDetail counterJob = JobBuilder.newJob(CounterIncreaseJob.class)
                    .withIdentity(JobKey.jobKey(counterIdStr, JOB_GROUP))
                    .usingJobData(CounterIncreaseJob.JOB_DATA_KEY_COUNTER_ID, counterIdStr)
                    .build();

            TriggerKey triggerKey = TriggerKey.triggerKey(counterIdStr, JOB_GROUP);

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .forJob(counterJob)
                    .startAt(counterScheduleApply.convertStartAt())
                    .withSchedule(cronSchedule(cronExpression))
                    .build();

            if (scheduler.getTrigger(triggerKey) == null) {
                scheduler.scheduleJob(counterJob, trigger);
            }
            else {
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            return new CounterSchedule(
                    counterScheduleApply.startAt(),
                    trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()),
                    counter);
        }
        catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong cron expression", e);
        }
        catch (SchedulerException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail scheduler action", e);
        }
    }

    public CounterSchedule getCounterSchedule(UUID counterId) {
        Counter counter = counterService.getCounter(counterId);
        String counterIdStr = counter.getId().toString();

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(counterIdStr, JOB_GROUP);

            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return new CounterSchedule(
                    trigger.getStartTime().toInstant().atZone(ZoneId.systemDefault()),
                    trigger.getNextFireTime().toInstant().atZone(ZoneId.systemDefault()),
                    counter
            );
        }
        catch (SchedulerException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail scheduler action", e);
        }
    }

    public void deleteCounterSchedule(UUID counterId) {
        Counter counter = counterService.getCounter(counterId);
        String counterIdStr = counter.getId().toString();

        TriggerKey triggerKey = TriggerKey.triggerKey(counterIdStr, JOB_GROUP);

        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            scheduler.unscheduleJob(triggerKey);
        }
        catch (SchedulerException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "fail scheduler action", e);
        }
    }

}
