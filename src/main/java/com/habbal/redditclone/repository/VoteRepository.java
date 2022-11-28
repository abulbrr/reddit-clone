package com.habbal.redditclone.repository;

import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.User;
import com.habbal.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByUserAndPostOrderByVoteIdDesc(User currentUser, Post post);
}
