package com.reddit.clone.models;

import java.util.Arrays;

import com.reddit.clone.exceptions.SpringRedditException;

public enum VoteType {
	UPVOTE(1), DOWNVOTE(-1),;
	
	private Integer direction;
	
	private VoteType(Integer direction) {
	}
	
	public static VoteType lookup(Integer direction) {
		return Arrays.stream(VoteType.values())
				.filter(value -> value.getDirection().equals(direction))
				.findAny()
				.orElseThrow(() -> new SpringRedditException("Vote not found !"));
	}

	public Integer getDirection() {
		return direction;
	}
	
}
