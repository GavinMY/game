package com.chinasofti.exception;

public class UserExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserExistException() {
		super();
	}

	public UserExistException(String message, Throwable cause) {
		
		super(message, cause);
	}

	public UserExistException(String message) {
		super(message);
	}

	public UserExistException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
