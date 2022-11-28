package com.habbal.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Comment {

    @Id
    private Long id;

    @NotEmpty
    private String text;

    @ManyToOne
    @JoinColumn(name="postId", referencedColumnName = "postId")
    private Post post;

    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User creator;
}
