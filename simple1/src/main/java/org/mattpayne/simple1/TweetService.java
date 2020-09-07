package org.mattpayne.simple1;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TweetService {
    private Twitter twitter;
    private Map<String,String> envMap;

    // Temp main thing
    public static void main(String[] arg) {
        try {
            TweetService ts = new TweetService();
            ts.run();
            System.out.println("Normal Termination.");
        } catch (Exception bland) {
            bland.printStackTrace();
        }
    }

    public TweetService() throws IOException {
        setup();
    }

    private void run() {
    }

    public void setup() throws IOException {
        String consumerKey = fetchFromEnvOrDotEnvFile("TWITTER_CONSUMER_KEY");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(fetchFromEnvOrDotEnvFile("TWITTER_CONSUMER_KEY"))
                .setOAuthConsumerSecret("your consumer secret")
                .setOAuthAccessToken("your access token")
                .setOAuthAccessTokenSecret("your access token secret");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    private String fetchFromEnvOrDotEnvFile(String key) throws IOException {
        //TODO(MGP): Get form environment
        if (this.envMap==null) {
            this.envMap = new HashMap<>();
            Properties properties = new Properties();
            properties.load(new FileReader(".env"));
        }
        return envMap.get(key);
    }
}
