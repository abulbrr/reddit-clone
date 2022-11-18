package com.habbal.redditclone.exception;


public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String message) {
        super(message);
    }

    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
}
