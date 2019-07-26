package com.sandip.vm.exceptions;

public class InvalidMenuOptionException extends RuntimeException {
	private static final long serialVersionUID = 4321663152360197900L;
	private String message;

	public InvalidMenuOptionException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
