package com.reddit.clone.servicesimpl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.dtos.PostRequest;
import com.reddit.clone.dtos.PostResponse;
import com.reddit.clone.exceptions.PostNotFoundException;
import com.reddit.clone.exceptions.SubredditNotFoundException;
import com.reddit.clone.mapper.PostMapper;
import com.reddit.clone.models.Post;
import com.reddit.clone.models.Subreddit;
import com.reddit.clone.models.User;
import com.reddit.clone.repository.PostRepository;
import com.reddit.clone.repository.SubredditRepository;
import com.reddit.clone.repository.UserRepository;
import com.reddit.clone.services.AuthService;
import com.reddit.clone.services.PostService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
	
	private PostRepository postRepository;
    private SubredditRepository subredditRepository;
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    private PostMapper postMapper;

	@Override
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String name) {
		User user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException(id.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
	}

	@Transactional(readOnly = true)
	@Override
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}

	@Override
	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		
		postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
	}
	
	

}
