package com.reddit.clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import static com.reddit.clone.models.VoteType.UPVOTE;
import static com.reddit.clone.models.VoteType.DOWNVOTE;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.clone.dtos.PostRequest;
import com.reddit.clone.dtos.PostResponse;
import com.reddit.clone.models.Post;
import com.reddit.clone.models.Subreddit;
import com.reddit.clone.models.User;
import com.reddit.clone.models.Vote;
import com.reddit.clone.models.VoteType;
import com.reddit.clone.repository.CommentRepository;
import com.reddit.clone.repository.VoteRepository;
import com.reddit.clone.services.AuthService;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mappings(value = 
    {@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
    @Mapping(target = "description", source = "postRequest.description"),
    @Mapping(target = "subreddit", source = "subreddit"),
    @Mapping(target = "voteCount", constant = "0"),
    @Mapping(target = "user", source = "user")}
    )
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mappings(value = 
    {@Mapping(target = "id", source = "postId"),
    @Mapping(target = "subredditName", source = "subreddit.name"),
    @Mapping(target = "userName", source = "user.userName"),
    @Mapping(target = "commentCount", expression = "java(commentCount(post))"),
    @Mapping(target = "duration", expression = "java(getDuration(post))"),
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))"),
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    })
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

}