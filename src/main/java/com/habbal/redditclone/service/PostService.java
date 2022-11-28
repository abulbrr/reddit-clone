package com.habbal.redditclone.service;

import com.habbal.redditclone.dto.PostRequest;
import com.habbal.redditclone.dto.PostResponse;
import com.habbal.redditclone.exception.PostNotFoundException;
import com.habbal.redditclone.exception.SubredditNotFoundException;
import com.habbal.redditclone.mapper.PostMapper;
import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.Subreddit;
import com.habbal.redditclone.model.User;
import com.habbal.redditclone.repository.PostRepository;
import com.habbal.redditclone.repository.SubredditRepository;
import com.habbal.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final SubredditRepository subredditRepository;

    private final UserRepository userRepository;
    private final AuthService authService;

    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();

        Post post = postMapper.map(postRequest, subreddit, currentUser);

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));

        return postRepository.findAllBySubreddit(subreddit).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByCreator(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());

    }
}
