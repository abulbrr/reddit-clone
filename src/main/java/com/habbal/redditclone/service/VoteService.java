package com.habbal.redditclone.service;

import com.habbal.redditclone.dto.VoteDto;
import com.habbal.redditclone.exception.PostNotFoundException;
import com.habbal.redditclone.exception.SpringRedditException;
import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.Vote;
import com.habbal.redditclone.repository.PostRepository;
import com.habbal.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.habbal.redditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(voteDto.getPostId()));

        // why do we need top?
        Optional<Vote> vote = voteRepository.findTopByUserAndPostOrderByVoteIdDesc(authService.getCurrentUser(), post);

        if(vote.isPresent() && vote.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + " for this post");
        }

        if(UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        }
        else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .build();
    }
}
