package org.mattpayne.simple1;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private Date when;


    public Tweet() {
    }

    public Tweet(String s) {
        this();
        text=s;
    }

    public String toString() {
        return String.format("%d: %s", id, text);
    }

}