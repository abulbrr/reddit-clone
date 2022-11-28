package com.habbal.redditclone.repository;

import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.Subreddit;
import com.habbal.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreator(User user);

    List<Post> findAllBySubreddit(Subreddit subreddit);
}
