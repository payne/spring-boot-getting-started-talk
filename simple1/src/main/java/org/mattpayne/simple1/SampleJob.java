package org.mattpayne.simple1;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Log4j2
@Component
public class SampleJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            TweetRepository tweetRepository = null;
            Tweet tweet = (Tweet) context.getJobDetail().getJobDataMap().get(Tweet.TWEET);
            if (tweet != null) {
                String tweetText = tweet.getText();
                log.info("Sample Job " + new java.util.Date());
                log.info("tweetText=" + tweetText);
                ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get(Tweet.AC);
                tweetRepository = applicationContext.getBean(TweetRepository.class);
                if (applicationContext != null) {
                    TweetService tweetService = applicationContext.getBean(TweetService.class);
                    log.info("tweetService=" + tweetService);
                    tweetService.updateStatus(tweetText);
                }
            }
            JobKey thisJob = context.getJobDetail().getKey();
            context.getScheduler().deleteJob(thisJob);
            log.info("Deleted job: " + thisJob);
            if (tweet != null) {
                tweetRepository.delete(tweet);
            }
        } catch (SchedulerException | TwitterException e) {
            e.printStackTrace();
        }
    }
}

