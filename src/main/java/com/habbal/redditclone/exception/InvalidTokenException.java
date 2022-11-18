package com.habbal.redditclone.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Invalid token!");
    }
}
