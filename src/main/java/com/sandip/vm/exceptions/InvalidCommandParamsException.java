package com.sandip.vm.exceptions;

public class InvalidCommandParamsException extends RuntimeException {

	private static final long serialVersionUID = 8914854020936644629L;
	private final String help;

	public InvalidCommandParamsException(String message, String help) {
		super(message);
		this.help = help;
	}

	public String getHelpMessage() {
		return this.help;
	}
}