package com.bookrec.app.exceptions.httpExeptions;

import org.springframework.http.HttpStatus;

import com.bookrec.app.exceptions.BookrecHttpException;

public class BadRequestException extends BookrecHttpException {

	private static final long serialVersionUID = 1L;

	private String message;
	
	private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;

	public BadRequestException(String message) {
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
