package com.sandip.vm.exceptions;

public class NotFullPaidException extends RuntimeException {

	private static final long serialVersionUID = 8525755416792112144L;
	private final String message;
	private final double remaining;

	public NotFullPaidException(String message, double remaining) {
		this.message = message;
		this.remaining = remaining;
	}

	@Override
	public String getMessage() {
		return message + remaining;
	}

	public double getRemaining() {
		return remaining;
	}

}
