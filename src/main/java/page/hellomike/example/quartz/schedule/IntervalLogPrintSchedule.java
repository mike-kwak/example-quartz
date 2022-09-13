package page.hellomike.example.quartz.schedule;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.*;

@Component
public class IntervalLogPrintSchedule {

    private static final JobKey JOB_KEY = JobKey.jobKey("interval_log_print");

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(IntervalLogPrintJob.class)
                .withIdentity(JOB_KEY)
                .usingJobData(IntervalLogPrintJob.JOB_NAME, "hello")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(
            @Value("${schedule.interval-log-print:0/5 * * * * ?}") String cronExpression
    ) {
        TriggerBuilder<Trigger> trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey("interval_log_print"))
                .forJob(JOB_KEY);

        if (CronExpression.isValidExpression(cronExpression)) {
            return trigger.withSchedule(cronSchedule(cronExpression))
                    .build();
        }

        return null;
    }

}
