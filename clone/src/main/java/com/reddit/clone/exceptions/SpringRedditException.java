package com.reddit.clone.exceptions;

public class SpringRedditException extends RuntimeException {

	public SpringRedditException() {
		super();
	}

	public SpringRedditException(String arg0) {
		super(arg0);
	}
	
	public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

}
