package com.reddit.clone.controllers;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.dtos.AuthenticationResponse;
import com.reddit.clone.dtos.LoginRequest;
import com.reddit.clone.dtos.RefreshTokenRequest;
import com.reddit.clone.dtos.RegisterRequest;
import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.services.AuthService;
import com.reddit.clone.services.RefreshTokenService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
		try {
		authService.signup(registerRequest);
		}catch(SpringRedditException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
	}
	
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }
    
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
    	try {
    		authService.login(loginRequest);
    	} catch(SpringRedditException e) {
    		return AuthenticationResponse
    				.builder()
    				.errorMessage(e.getMessage())
    				.build();
    	}
        return authService.login(loginRequest);
    }
    
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }

}
