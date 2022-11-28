package com.habbal.redditclone.exception;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String subredditName) {
        super("Subreddit " + subredditName + " was not found!");
    }
}
