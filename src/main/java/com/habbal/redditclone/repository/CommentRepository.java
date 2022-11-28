package com.habbal.redditclone.repository;

import com.habbal.redditclone.model.Comment;
import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByCreator(User user);
}
