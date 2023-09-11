package com.bookrec.app.exceptions.interests;

import com.bookrec.app.exceptions.httpExeptions.NotFoundException;

public class InterestNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	private static String message;
	
	public InterestNotFoundException(Long interestId) {
		super(message);
		message = String.format("Interest of id %d not found", interestId);
	}

}
