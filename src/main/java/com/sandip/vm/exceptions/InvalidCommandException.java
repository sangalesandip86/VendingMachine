package com.sandip.vm.exceptions;

public class InvalidCommandException extends RuntimeException {

	private static final long serialVersionUID = 7959079134067832490L;
	private final String help;

	public InvalidCommandException(String message, String help) {
		super(message);
		this.help = help;
	}

	public String getHelpMessage() {
		return this.help;
	}
}