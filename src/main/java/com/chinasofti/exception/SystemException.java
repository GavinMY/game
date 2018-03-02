package com.chinasofti.exception;

/**
 * 验证异常
 * 
 * @author Season
 *
 */
public class SystemException extends Exception {

	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return "系统错误：" + super.getMessage();
	}
	
	
}
