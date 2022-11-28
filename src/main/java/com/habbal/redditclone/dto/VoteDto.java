package com.habbal.redditclone.dto;

import com.habbal.redditclone.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDto {
    private Long postId;
    private VoteType voteType;
}

