package com.habbal.redditclone.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private int direction;
    VoteType(Integer direction) {}

    public Integer getDirection() {
        return direction;
    }
}
