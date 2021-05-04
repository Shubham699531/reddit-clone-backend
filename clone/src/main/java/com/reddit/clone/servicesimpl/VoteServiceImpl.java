package com.reddit.clone.servicesimpl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.reddit.clone.models.VoteType.UPVOTE;

import com.reddit.clone.dtos.VoteDto;
import com.reddit.clone.exceptions.PostNotFoundException;
import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.models.Post;
import com.reddit.clone.models.Vote;
import com.reddit.clone.repository.PostRepository;
import com.reddit.clone.repository.VoteRepository;
import com.reddit.clone.services.AuthService;
import com.reddit.clone.services.VoteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
	
	private VoteRepository voteRepository;
    private PostRepository postRepository;
    @Autowired
    private AuthService authService;

	@Override
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
	}
	
	private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

}
