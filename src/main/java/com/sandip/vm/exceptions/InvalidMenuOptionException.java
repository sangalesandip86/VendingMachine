package com.sandip.vm.exceptions;

public class InvalidMenuOptionException extends RuntimeException {
	private String message;

	public InvalidMenuOptionException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
