package com.sandip.vm.exceptions;

public class NotFullPaidException extends RuntimeException {

	private static final long serialVersionUID = 8525755416792112144L;
	private final String message;
	private final long remaining;

	public NotFullPaidException(String message, long remaining) {
		this.message = message;
		this.remaining = remaining;
	}

	@Override
	public String getMessage() {
		return message + remaining;
	}

	public long getRemaining() {
		return remaining;
	}

}
