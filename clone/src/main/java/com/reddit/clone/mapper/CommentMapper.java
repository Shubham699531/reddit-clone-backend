package com.reddit.clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.reddit.clone.dtos.CommentsDto;
import com.reddit.clone.models.Comment;
import com.reddit.clone.models.Post;
import com.reddit.clone.models.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mappings(value = 
    {@Mapping(target = "id", ignore = true),
    @Mapping(target = "text", source = "commentsDto.text"),
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
    @Mapping(target = "post", source = "post"),
    @Mapping(target = "user", source = "user")})
    Comment map(CommentsDto commentsDto, Post post, User user);

	@Mappings(value = 
    {@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())"),
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")})
    CommentsDto mapToDto(Comment comment);
}