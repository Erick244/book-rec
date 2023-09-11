package com.bookrec.app.exceptions.books;

import com.bookrec.app.exceptions.httpExeptions.BadRequestException;

public class BookInterestsIsFullException extends BadRequestException {
	private static final long serialVersionUID = 1L;
	
	private static final String message = "This book has reached the maximum number of interests";
	
	public BookInterestsIsFullException() {
		super(message);
	}
}
