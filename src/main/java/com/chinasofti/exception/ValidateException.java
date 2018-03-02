package com.chinasofti.exception;

/**
 * 验证异常
 * 
 * @author Gavin
 *
 */
public class ValidateException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidateException() {
		super();
	}

	public ValidateException(String message, Throwable cause) {
		
		super(message, cause);
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	
}
