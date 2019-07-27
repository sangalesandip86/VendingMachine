package com.sandip.vm.constants;

public class Constant {

	public static final String SLOT_MESSAGE = "Slot ";

	public static final String SPACE = "";

	public static final String CHOICE = ">>";

	public static final String INSERT_COIN = "INSERT";

	public static final String SELECT_PRODUCT = "SELECT";

	public static final String MAINTENANCE = "MAINTAIN";
	public static final String QUIT = "QUIT";

	public static final String CANCEL = "CANCEL";

	public static final String COLLECT_ITEM_AND_CHANGE = "COLLECT";

	public static final String INVALID_PRODUCT_SLOT = "Invalid product slot";

	public static final String PRODUCT_NOT_PRESENT_ON_SELECTED_SLOT = "Product not present on selected slot";

	public static final String ERROR_PLEASE_SET_PRODUCT_PRICE_BEFORE_SETTING_QUANTITY = "Error : Please set product price before setting quantity";

	public static final String ERROR_PRODUCT_SLOT_DOES_NOT_EXIST = "Error : Product slot does not exist";

	public static final String NOT_SUFFICIENT_CHANGE_FOR_AMOUNT = "Not Sufficient change for amount";

	public static final String DISPLAY = "DISPLAY";

	public static final String SOLD_OUT = "Sold out";

	public static final String PRODUCT_SLOT_MUST_BE_VALID_POSITIVE_NUMBER = "Product slot must be valid positive number";

	public static final String INSUFFICIENT_FUNDS_PLEASE_FEED_MORE_MONEY = "Insufficient Funds. Please feed more money.";

	public static final String PLEASE_SELECT_A_PRODUCT_FOR_PURCHASE = "Please select a product for purchase.";

	public static final String INVALID_CHOICE = "Invalid choice";

	public static final String INVALID_COMMAND = "Invalid Command";
	
	public static final String IS_NOT_A_VALID_NUMBER = " is not a valid number";

	public static final String INVALID_COIN_DENOMINATION = "Invalid coin denomination, Machine accepts only 10, 20, 50, 100, 200 PENCE denominations";

	public static final String SELECT_COMMAND_EXPECT_EXACT_1_PRODUCT_SLOT = "SELECT command expect exact 1 product slot";

	public static final String SUPPORTED_COINS = "Supported Coins : 200, 100, 50, 20, 10      All denomination in PENCE(£1=100)";

	public static final String COMMAND_HELP_MESSAGE = "\n-- Commands Guide--\nINSERT {coin1, coin2...coinN}\t- To insert Coins, denomination is in pence e.g £1 = 100, Command allows to add more than one coin at a time e.g INSERT 100 200 20 50 10"
			+ "\nSELECT {productSlot} \t- To select product slot "
			+ "\nCOLLECT \t- Collect Item and change e.g SELECT 1" + "\nMAINTAIN\t- Maintenance"
			+ "\nCANCEL \t\t- Cancel purchase and refund inserted coins" + "\nQUIT\t\t- Quit\n";

	private Constant() {
	}
}
