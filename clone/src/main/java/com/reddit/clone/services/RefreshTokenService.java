package com.reddit.clone.services;

import com.reddit.clone.models.RefreshToken;

public interface RefreshTokenService {

	void deleteRefreshToken(String refreshToken);

	RefreshToken generateRefreshToken();

	void validateRefreshToken(String refreshToken);

}
