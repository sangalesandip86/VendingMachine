package com.sandip.vm.exceptions;

public class NotSufficientChangeException extends RuntimeException {
	private static final long serialVersionUID = 9218708991879774916L;
	private final String message;

	public NotSufficientChangeException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
