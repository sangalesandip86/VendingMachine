package com.sandip.vm;

import java.util.Arrays;
import java.util.Scanner;

import com.sandip.vm.constants.Constant;
import com.sandip.vm.exceptions.InvalidCommandException;

public class ElderVendingMachine {

	private static final String COMMAND_HELP_MESSAGE = "\n-- Commands Guide--\nINSERT 100\t- To insert multiple space separated Coins denom, it suppports denomination in PENCE £1 = 100\nSELECT 0 \t- To select product slot \nCOLLECT \t- Collect Item and change\nMAINTAIN\t- Maintenance\nCANCEL \t\t- Cancel purchase and refund inserted coins\nQUIT\t\t- Quit\n";

	private static final VendingMachine vm = VendingMachineImpl.getInstance(4, Arrays.asList(0.1, 0.2, 0.5, 1.0, 2.0));

	public static void main(String[] args) {

		vm.initializeInventory();
		vm.initializeMoneyBank();
		vm.whatsAvailable();

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("Enter Command >> ");
				String command = (scanner.nextLine().trim()).toUpperCase();
				try {
					acceptCommand(command);
				} catch (IllegalArgumentException iae) {
					System.out.println(iae.getMessage());
				} catch (InvalidCommandException ice) {
					System.out.println(ice.getMessage());
					System.out.println(ice.getHelpMessage());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public static void acceptCommand(String command) {

		String[] commandParams = (command.trim()).split("\\s+");
		String mainCommand = commandParams[0];

		String[] params = Arrays.copyOfRange(commandParams, 1, commandParams.length);

		switch (mainCommand) {
		case Constant.INSERT_COIN:
			vm.insertCoins(params);
			break;
		case Constant.SELECT_PRODUCT:
			vm.selectProduct(params);
			break;
		case Constant.COLLECT_ITEM_AND_CHANGE:
			vm.collectItemAndChange();
			break;
		case Constant.MAINTENANCE:
			vm.maintenance();
			break;
		case Constant.CANCEL:
			vm.cancel();
			break;
		case Constant.QUIT:
			vm.quit();
			break;
		default:
			throw new InvalidCommandException("Invalid Command", COMMAND_HELP_MESSAGE);
		}

	}
}
