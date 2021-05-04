package com.reddit.clone.services;

import java.util.List;

import com.reddit.clone.dtos.PostRequest;
import com.reddit.clone.dtos.PostResponse;

public interface PostService {

	List<PostResponse> getPostsByUsername(String name);

	List<PostResponse> getPostsBySubreddit(Long id);

	PostResponse getPost(Long id);

	List<PostResponse> getAllPosts();

	void save(PostRequest postRequest);

}
