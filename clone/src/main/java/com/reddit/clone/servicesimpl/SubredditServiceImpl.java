package com.reddit.clone.servicesimpl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.dtos.SubredditDto;
import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.mapper.SubredditMapper;
import com.reddit.clone.models.Subreddit;
import com.reddit.clone.repository.SubredditRepository;
import com.reddit.clone.services.SubredditService;

import lombok.AllArgsConstructor;

@Service
public class SubredditServiceImpl implements SubredditService {
	
	private SubredditRepository subredditRepository;
    private SubredditMapper subredditMapper;

	@Override
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getSubredditId());
        return subredditDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
	}

	@Override
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
	}

}
