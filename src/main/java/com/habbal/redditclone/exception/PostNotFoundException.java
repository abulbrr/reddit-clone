package com.habbal.redditclone.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId) {
        super("Post with id: " + postId + " was not found");
    }
}
