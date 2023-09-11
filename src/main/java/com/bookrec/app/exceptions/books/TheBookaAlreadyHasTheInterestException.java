package com.bookrec.app.exceptions.books;

import com.bookrec.app.exceptions.httpExeptions.NotAcceptableException;

public class TheBookaAlreadyHasTheInterestException extends NotAcceptableException {
	private static final long serialVersionUID = 1L;
	
	private static String message;
	
	public TheBookaAlreadyHasTheInterestException(Long interestId) {
		super(message);
		
		message = String.format("The book already has the intersse of id %d", interestId);
	}

}
