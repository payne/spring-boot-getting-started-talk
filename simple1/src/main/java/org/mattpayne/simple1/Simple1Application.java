package org.mattpayne.simple1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

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
}
