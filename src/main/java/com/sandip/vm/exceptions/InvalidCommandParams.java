package com.sandip.vm.exceptions;

public class InvalidCommandParams extends RuntimeException {

	private static final long serialVersionUID = 8914854020936644629L;
	private final String help;

	public InvalidCommandParams(String message, String help) {
		super(message);
		this.help = help;
	}

	public String getHelpMessage() {
		return this.help;
	}
}