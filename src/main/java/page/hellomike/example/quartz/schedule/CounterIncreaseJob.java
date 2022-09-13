package page.hellomike.example.quartz.schedule;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import page.hellomike.example.quartz.service.CounterService;

import java.util.UUID;

public class CounterIncreaseJob extends QuartzJobBean {

    public static final String JOB_DATA_KEY_COUNTER_ID = "counter_id";

    private final CounterService counterService;

    public CounterIncreaseJob(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String counterIdString = jobDataMap.getString(JOB_DATA_KEY_COUNTER_ID);
        UUID counterId = UUID.fromString(counterIdString);
        counterService.increaseCounter(counterId);
    }
}
