package com.reddit.clone.servicesimpl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.exceptions.SpringRedditException;
import com.reddit.clone.models.RefreshToken;
import com.reddit.clone.repository.RefreshTokenRepository;
import com.reddit.clone.services.RefreshTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService{
	
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public void deleteRefreshToken(String refreshToken) {
		refreshTokenRepository.deleteByToken(refreshToken);
	}

	@Override
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public void validateRefreshToken(String refreshToken) {
		refreshTokenRepository.findByToken(refreshToken)
        .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
	}

}
