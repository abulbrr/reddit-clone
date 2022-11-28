package com.habbal.redditclone.repository;

import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.User;
import com.habbal.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByUserAndPostOrderByVoteIdDesc(User currentUser, Post post);
}
