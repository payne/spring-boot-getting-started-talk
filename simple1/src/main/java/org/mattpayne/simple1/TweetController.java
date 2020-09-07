package org.mattpayne.simple1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TweetController {

    @GetMapping("/")
    public String tweetinForm(Model model) {
        model.addAttribute("tweet",new Tweet());
        return "tweetin";
    }

    @PostMapping("/")
    public String tweetSubmit(@ModelAttribute Tweet tweet, TweetService tweetService, Model model) {
        System.out.println("Recieved: " + tweet);
        try {
            System.out.println("Would tweet: " + tweet.getText());
            tweetService.updateStatus(tweet.getText());
        } catch (Exception bland) {
            bland.printStackTrace();
        }
        model.addAttribute("tweet",tweet);
        return "tweetin";
    }
}
