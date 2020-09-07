package org.mattpayne.simple1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@SpringBootApplication
public class Simple1Application implements CommandLineRunner {

	@Autowired
	private TweetRepository tweetRepository;

	public static void main(String[] args) {
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
	public JobDetail jobDetail() {
		return JobBuilder.newJob().ofType(SampleJob.class)
				.storeDurably()
				.withIdentity("Qrtz_Job_Detail")
				.withDescription("Invoke Sample Job service...")
				.build();
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
