package org.mattpayne.simple1;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static org.quartz.CronScheduleBuilder.*;

@Log4j2
@Controller
public class TweetController {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private ApplicationContext applicationContext;
    private TweetRepository tweetRepository;

    public TweetController(ApplicationContext ac, TweetRepository tr) {
        this.applicationContext = ac;
        this.tweetRepository = tr;
    }


    @GetMapping("/")
    public String tweetinForm(Model model) {
        model.addAttribute(Tweet.TWEET, new Tweet());
        return "tweetin";
    }

    @PostMapping("/")
    public String tweetSubmit(@ModelAttribute Tweet tweet, TweetService tweetService, Model model) {
        log.info("Recieved: " + tweet);
        try {
            log.info(String.format("Will tweet: " + tweet.getText() + " per quartz='%s'", tweet.getQuartz()));
            tweetRepository.save(tweet);

            JobDetail jobDetail = JobBuilder.newJob().ofType(SampleJob.class)
                    .storeDurably()
                    .withIdentity("Demo from TweetController at " + new java.util.Date() + " " + UUID.randomUUID())
                    .withDescription("Created with quartz: " + tweet.getQuartz())
                    .build();
            jobDetail.getJobDataMap().put(Tweet.TWEET, tweet);
            jobDetail.getJobDataMap().put(Tweet.AC, applicationContext);

            Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                    .withIdentity("Idenity for " + tweet.getQuartz() + " at " + new java.util.Date() + " " + UUID.randomUUID())
                    .withSchedule(cronSchedule(tweet.getQuartz())) // e.g. 0 0 12 ? * * *
                    .build();
            Scheduler sched = schedulerFactory.getScheduler();
            sched.scheduleJob(jobDetail, trigger);
            sched.start();

//            tweetService.updateStatus(tweet.getText());
        } catch (Exception bland) {
            bland.printStackTrace();
        }
        model.addAttribute(Tweet.TWEET, tweet);
        return "tweetin";
    }
}
