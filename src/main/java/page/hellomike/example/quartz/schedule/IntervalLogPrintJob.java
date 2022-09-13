package page.hellomike.example.quartz.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import page.hellomike.example.quartz.service.LogPrintService;

import java.time.LocalDateTime;

public class IntervalLogPrintJob extends QuartzJobBean {

    public static final String JOB_NAME = "job_name";

    private final LogPrintService logPrintService;

    public IntervalLogPrintJob(LogPrintService logPrintService) {
        this.logPrintService = logPrintService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jobName = context.getMergedJobDataMap().getString(JOB_NAME);
        logPrintService.print(jobName, LocalDateTime.now());
    }

}
