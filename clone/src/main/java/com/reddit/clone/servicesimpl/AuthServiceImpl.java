package com.reddit.clone.servicesimpl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.dtos.AuthenticationResponse;
import com.reddit.clone.dtos.LoginRequest;
import com.reddit.clone.dtos.RefreshTokenRequest;
import com.reddit.clone.dtos.RegisterRequest;
import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.models.NotificationEmail;
import com.reddit.clone.models.User;
import com.reddit.clone.models.VerificationToken;
import com.reddit.clone.repository.UserRepository;
import com.reddit.clone.repository.VerificationTokenRepository;
import com.reddit.clone.security.JwtProvider;
import com.reddit.clone.services.AuthService;
import com.reddit.clone.services.MailService;
import com.reddit.clone.services.RefreshTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private PasswordEncoder passwordEncoder ;
	
	private UserRepository userRepository;
	
	private VerificationTokenRepository verificationTokenRepository;
	
	private AuthenticationManager authenticationManager;
	
	private JwtProvider jwtProvider;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private MailService mailService;
	

	@Override
	public void signup(RegisterRequest registerRequest) throws SpringRedditException{
		User user = new User();
		user.setUserName(registerRequest.getUserName());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		User existingUser = userRepository.findByEmail(registerRequest.getEmail());
		if(existingUser==null) {
			userRepository.save(user);
			String token = generateVerificationToken(user);
			mailService.sendMail(new NotificationEmail("Please Activate your Account",
	                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
	                "please click on the below url to activate your account : " +
	                "http://localhost:8001/api/auth/accountVerification/" + token));
		}else {
			if(!existingUser.isEnabled()) {
				throw new SpringRedditException("You are already registered. Kindly activate your account"
						+ " by clicking on the link provided on your mail.");
			}else {
				throw new SpringRedditException("You are already registered. Kindly login.");
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = 
				(org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userRepository.findByUserName(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}
	
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String userName = verificationToken.getUser().getUserName();
		User user = userRepository.findByUserName(userName).orElseThrow(() -> new SpringRedditException("User not found with name - " + userName));
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		return token;
	}
	
	@Override
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
	}

	@Override
	public AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException{
		Optional<User> user = userRepository.findByUserName(loginRequest.getUserName());
		if(user.isPresent()) {
			if(!user.get().isEnabled()) {
				throw new SpringRedditException("Kindly activate your account"
						+ " first. Click on the link provided on your mail."); 
			}
		}
		Authentication authenticate = null;
		try {
			authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), 
							loginRequest.getPassword()));
		} catch (AuthenticationException e) {
			throw new SpringRedditException("Wrong login credentials."); 
		}
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.userName(loginRequest.getUserName())
				.build();
	}

	@Override
	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUserName());
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.userName(refreshTokenRequest.getUserName())
				.build();
	}
	
	@Override
	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

}
