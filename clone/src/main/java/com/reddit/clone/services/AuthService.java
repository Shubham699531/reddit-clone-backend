package com.reddit.clone.services;

import javax.validation.Valid;

import com.reddit.clone.dtos.AuthenticationResponse;
import com.reddit.clone.dtos.LoginRequest;
import com.reddit.clone.dtos.RefreshTokenRequest;
import com.reddit.clone.dtos.RegisterRequest;
import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.models.User;

public interface AuthService {
	
	void signup(RegisterRequest registerRequest) throws SpringRedditException;

	void verifyAccount(String token);

	AuthenticationResponse login(LoginRequest loginRequest) throws SpringRedditException;

	AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest);
	
	User getCurrentUser();

	boolean isLoggedIn();
}
