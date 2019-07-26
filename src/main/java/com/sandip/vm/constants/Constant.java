package com.sandip.vm.constants;

public class Constant {

	// Error message for invalid code
	public static final String INVALID_CODE_MESSAGE = "Invalid code for vending machine item";

	// Error message for amounts < 0
	public static final String INVALID_AMOUNT_MESSAGE = "Invalid amount.  Amount must be >= 0";

	// Slot part of error message
	public static final String SLOT_MESSAGE = "Slot ";

	// Already occupied part of error message
	public static final String ALREADY_OCCUPIED_MESSAGE = " already occupied";

	// "is empty" part of error message
	public static final String IS_EMPTY_MESSAGE = " is empty -- cannot remove item";

	public static final String SPACE = "";

	public static final String CHOICE = ">>";

	public static final String INSERT_COIN = "INSERT";

	public static final String SELECT_PRODUCT = "SELECT";

	public static final String MAINTENANCE = "MAINTAIN";
	public static final String QUIT = "QUIT";

	public static final String CANCEL = "CANCEL";

	public static final String COLLECT_ITEM_AND_CHANGE = "COLLECT";

	private Constant() {
	}
}
