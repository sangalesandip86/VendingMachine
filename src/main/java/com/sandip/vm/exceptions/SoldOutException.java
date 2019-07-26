package com.sandip.vm.exceptions;

/**
 * 
 * @author sandip.p.sangale
 *
 */
public class SoldOutException extends RuntimeException {
	private static final long serialVersionUID = 5940861816185959813L;
	private final String message;

	public SoldOutException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}