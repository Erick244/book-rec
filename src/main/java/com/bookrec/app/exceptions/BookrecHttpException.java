package com.bookrec.app.exceptions;

import org.springframework.http.HttpStatus;

public class BookrecHttpException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;
	
	private HttpStatus statusCode;
	
	public BookrecHttpException() {
		// TODO Auto-generated constructor stub
	}

	public BookrecHttpException(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
}
