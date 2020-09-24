package org.mattpayne.simple1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@SpringBootApplication
public class Simple1Application implements CommandLineRunner {
	private TweetRepository tweetRepository;

	public Simple1Application(TweetRepository tweetRepository) {
		this.tweetRepository=tweetRepository;
	}

	public static void main(String[] args) {
		// More common to set properties on the command line with -D e.g.
		// -Dspring.profiles.active=dev
		System.setProperty("spring.profiles.default","dev");
		SpringApplication.run(Simple1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<String> messages = Arrays.asList("Hello world!", "Java is fun", "Spring makes it better");
		for (String message: messages) {
			Tweet tweet = new Tweet(message);
			tweetRepository.save(tweet);
		}
		Iterable<Tweet> allTweets = tweetRepository.findAll();
		allTweets.forEach(System.out::println);
	}

	@Bean
	public TweetService oneTweetService() {
		try {
			return new TweetService();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
			return null; //TODO(MGP): Clean up!
		}
	}

	@Bean
	public JobDetail jobDetail() {
		JobDetail jd = JobBuilder.newJob().ofType(SampleJob.class)
				.storeDurably()
				.withIdentity("Qrtz_Job_Detail")
				.withDescription("Invoke Sample Job service...")
				.build();
		// jd.getJobDataMap().put("tweet","Tweet from a bean; created at " + new java.util.Date());
		return jd;
	}

	@Bean
	public Trigger trigger(JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job)
				.withIdentity("Qrtz_Trigger")
				.withDescription("Sample trigger")
				.withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(60))
				.build();
	}

}
