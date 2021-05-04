package com.reddit.clone.services;

import java.util.List;

import com.reddit.clone.dtos.SubredditDto;

public interface SubredditService {

	SubredditDto save(SubredditDto subredditDto);

	List<SubredditDto> getAll();

	SubredditDto getSubreddit(Long id);

}
