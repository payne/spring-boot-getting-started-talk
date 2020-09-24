package org.mattpayne.simple1;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SampleJob implements Job {

   // @Autowired
   // private SampleJobService jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //jobService.executeSampleJob();
        Object tweetText = context.getJobDetail().getJobDataMap().get("tweet");
        log.info("Sample Job " + new java.util.Date());
        log.info("tweetText=" + tweetText);
        JobKey thisJob=context.getJobDetail().getKey();
        try {
            context.getScheduler().deleteJob(thisJob);
            log.info("Deleted job: " + thisJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

