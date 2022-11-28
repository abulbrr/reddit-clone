package com.habbal.redditclone.mapper;

import com.habbal.redditclone.dto.CommentDto;
import com.habbal.redditclone.model.Comment;
import com.habbal.redditclone.model.Post;
import com.habbal.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "creator", source = "user")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getCreator().getUsername())")
    CommentDto mapToDto(Comment comment);
}
