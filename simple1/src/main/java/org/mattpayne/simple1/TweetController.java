package org.mattpayne.simple1;

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
    public String tweetSubmit(@ModelAttribute Tweet tweet, Model model) {
        System.out.println("Recieved: " + tweet);
        model.addAttribute("tweet",tweet);
        return "tweetin";
    }
}
