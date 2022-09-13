package page.hellomike.example.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogPrintService {

    private static final Logger log = LoggerFactory.getLogger(LogPrintService.class);

    public void print(String jobName, LocalDateTime printAt) {
        log.info("{} job: log print success at {}", jobName, printAt);
    }

}
