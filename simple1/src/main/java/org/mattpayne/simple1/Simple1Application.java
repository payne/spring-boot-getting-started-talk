package org.mattpayne.simple1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Simple1Application implements CommandLineRunner {
	private TweetRepository tweetRepository;

	public Simple1Application(TweetRepository tweetRepository) {
		this.tweetRepository=tweetRepository;
	}

	public static void main(String[] args) {
		// More common to set properties on the command line with -D e.g.
		// -Dspring.profiles.active=dev
		System.setProperty("spring.profiles.active","dev");
		SpringApplication.run(Simple1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    List<Tweet> tweets = Arrays.asList(new Tweet("Java is fun!","0 35 11 ? * THU *"),
				new Tweet("Python is the future!", "0 45 11 ? * THU *"));
		for (Tweet tweet: tweets) {
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
}
