package com.reddit.clone.servicesimpl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reddit.clone.dtos.CommentsDto;
import com.reddit.clone.exceptions.PostNotFoundException;
import com.reddit.clone.mapper.CommentMapper;
import com.reddit.clone.models.Comment;
import com.reddit.clone.models.NotificationEmail;
import com.reddit.clone.models.Post;
import com.reddit.clone.models.User;
import com.reddit.clone.repository.CommentRepository;
import com.reddit.clone.repository.PostRepository;
import com.reddit.clone.repository.UserRepository;
import com.reddit.clone.services.AuthService;
import com.reddit.clone.services.CommentService;
import com.reddit.clone.services.MailService;

import lombok.AllArgsConstructor;

@Service
public class CommentServiceImpl implements CommentService {
	
	private static final String POST_URL = "";
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    private CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private MailService mailService;

	@Override
	public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
	}

	@Override
	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
	}

	@Override
	public List<CommentsDto> getAllCommentsForUser(String userName) {
		  User user = userRepository.findByUserName(userName)
	                .orElseThrow(() -> new UsernameNotFoundException(userName));
	        return commentRepository.findAllByUser(user)
	                .stream()
	                .map(commentMapper::mapToDto)
	                .collect(toList());
	}
	
	private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUserName() + " Commented on your post", user.getEmail(), message));
    }

}
