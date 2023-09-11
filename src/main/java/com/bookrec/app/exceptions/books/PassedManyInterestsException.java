package com.bookrec.app.exceptions.books;

import com.bookrec.app.exceptions.httpExeptions.NotAcceptableException;

public class PassedManyInterestsException extends NotAcceptableException {
	private static final long serialVersionUID = 1L;
	
	private static String message;
	
	private final int maxInterests = 5;
	
	public PassedManyInterestsException(int numberOfInterestsAvailable, int amountOfBookInterests) {
		super(message);
		
		message = String.format("The amount of additional interests must be equal to or less than %d, because the book already has %d of %d interests.", numberOfInterestsAvailable, amountOfBookInterests, maxInterests);		
	}
}
