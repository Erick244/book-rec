package com.bookrec.app.exceptions.httpExeptions;

import org.springframework.http.HttpStatus;

import com.bookrec.app.exceptions.BookrecHttpException;

public class NotFoundException extends BookrecHttpException {
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	private static final HttpStatus statusCode = HttpStatus.NOT_FOUND;

	public NotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}
}
