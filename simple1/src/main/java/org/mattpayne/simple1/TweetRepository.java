package org.mattpayne.simple1;

import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
    Tweet findById(long id);
}
