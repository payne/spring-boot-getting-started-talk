package org.mattpayne.simple1;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Log4j2
@Component
public class SampleJob implements Job {
    /*
    private final ApplicationContext applicationContext;

    public SampleJob(ApplicationContext ac) {
        this.applicationContext = ac;
    }
     */

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //jobService.executeSampleJob();
        /*
        TweetService tweetService = applicationContext.getBean(TweetService.class);
        log.info("tweetService="+tweetService);
         */
        String tweetText = (String) context.getJobDetail().getJobDataMap().get("tweet");
        log.info("Sample Job " + new java.util.Date());
        log.info("tweetText=" + tweetText);
        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("ac");
            if (applicationContext != null) {
                TweetService tweetService = applicationContext.getBean(TweetService.class);
                log.info("tweetService=" + tweetService);
                tweetService.updateStatus(tweetText);
            }
            JobKey thisJob = context.getJobDetail().getKey();
            context.getScheduler().deleteJob(thisJob);
            log.info("Deleted job: " + thisJob);
        } catch (SchedulerException | TwitterException e) {
            e.printStackTrace();
        }
    }
}

