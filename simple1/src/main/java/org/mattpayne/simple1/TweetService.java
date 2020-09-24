package org.mattpayne.simple1;

import lombok.extern.log4j.Log4j2;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Log4j2
public class TweetService {
    private Twitter twitter;
    private Map<String, String> envMap;

    // Temp main thing
    public static void main(String[] arg) {
        try {
            TweetService ts = new TweetService();
            ts.run();
            log.info("Normal Termination.");
        } catch (Exception bland) {
            bland.printStackTrace();
        }
    }

    private void run() throws TwitterException {
        Status status = twitter.updateStatus("First tweet. at " + new java.util.Date());
        log.info("Tweet sent? " + status.getText());
    }

    public TweetService() throws IOException {
        setup();
    }

    public String updateStatus(String statusText) throws TwitterException {
        Status status = twitter.updateStatus(statusText);
        return status.getText();
    }

    private void setup() throws IOException {
        String consumerKey = fetchFromDotEnvFile("TWITTER_CONSUMER_KEY");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(fetchFromDotEnvFile("TWITTER_CONSUMER_KEY"))
                .setOAuthConsumerSecret(fetchFromDotEnvFile("TWITTER_CONSUMER_SECRET_KEY"))
                .setOAuthAccessToken(fetchFromDotEnvFile("TWITTER_ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(fetchFromDotEnvFile("TWITTER_ACCESS_TOKEN_SECRET"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    private String fetchFromDotEnvFile(String fetchThisKey) throws IOException {
        if (this.envMap == null) {
            this.envMap = new HashMap<>();
            Properties properties = new Properties();
            properties.load(new FileReader(".env"));
            Enumeration<Object> keys = properties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                String value = (String) properties.get(key);
                envMap.put(key, value);
            }
        }
        return envMap.get(fetchThisKey);
    }
}
