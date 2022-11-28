package com.habbal.redditclone.service;

import com.habbal.redditclone.dto.CommentDto;
import com.habbal.redditclone.exception.PostNotFoundException;
import com.habbal.redditclone.mapper.CommentMapper;
import com.habbal.redditclone.model.Comment;
import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.User;
import com.habbal.redditclone.repository.CommentRepository;
import com.habbal.redditclone.repository.PostRepository;
import com.habbal.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;


    public void createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId()));

        User currentUser = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentDto, post, currentUser);

        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return commentRepository.findByCreator(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
