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

import static org.quartz.CronScheduleBuilder.*;

@Log4j2
@Controller
public class TweetController {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private ApplicationContext applicationContext;
    private TweetRepository tweetRepository;

    public TweetController(ApplicationContext ac, TweetRepository tr) {
        this.applicationContext=ac;
        this.tweetRepository = tr;
    }


    @GetMapping("/")
    public String tweetinForm(Model model) {
        model.addAttribute("tweet", new Tweet());
        return "tweetin";
    }

    @PostMapping("/")
    public String tweetSubmit(@ModelAttribute Tweet tweet, TweetService tweetService, Model model) {
        log.info("Recieved: " + tweet);
        try {
            log.info(String.format("Will tweet: " + tweet.getText() + " per quartz='%s'", tweet.getQuartz()));
            tweetRepository.save(tweet);

            JobDetail jdetail = JobBuilder.newJob().ofType(SampleJob.class)
                    .storeDurably()
                    .withIdentity("Demo from TweetController at "+new java.util.Date())
                    .withDescription("Created with quartz: " + tweet.getQuartz())
                    .build();
            jdetail.getJobDataMap().put("tweet", tweet.getText());
            jdetail.getJobDataMap().put("ac",applicationContext);

            Trigger trigger = TriggerBuilder.newTrigger().forJob(jdetail)
                    .withIdentity("Idenity for " + tweet.getQuartz()+" at " + new java.util.Date())
                    .withSchedule(cronSchedule(tweet.getQuartz()))
                    .build();
            Scheduler sched = schedulerFactory.getScheduler();
            sched.scheduleJob(jdetail,trigger);
            sched.start();

//            tweetService.updateStatus(tweet.getText());
        } catch (Exception bland) {
            bland.printStackTrace();
        }
        model.addAttribute("tweet", tweet);
        return "tweetin";
    }
}
