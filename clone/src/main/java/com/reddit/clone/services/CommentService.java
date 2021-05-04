package com.reddit.clone.services;

import java.util.List;

import com.reddit.clone.dtos.CommentsDto;

public interface CommentService {

	void save(CommentsDto commentsDto);

	List<CommentsDto> getAllCommentsForPost(Long postId);

	List<CommentsDto> getAllCommentsForUser(String userName);

}
