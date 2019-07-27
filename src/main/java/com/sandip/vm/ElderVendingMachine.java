package com.sandip.vm;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.sandip.vm.constants.Constant;
import com.sandip.vm.exceptions.InvalidCommandException;

public class ElderVendingMachine {

	private static final VendingMachine vm = VendingMachineImpl.getInstance(4, Arrays.asList(0.1, 0.2, 0.5, 1.0, 2.0));

	public static void main(String[] args) {

		printMessage(Constant.COMMAND_HELP_MESSAGE);
		printMessage(Constant.SUPPORTED_COINS);
		printMessage(Constant.SPACE);
		vm.initializeInventory();
		vm.initializeMoneyBank();
		vm.whatsAvailable();

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				printMessage("Enter Command >> ");
				try {
					String command = (scanner.nextLine().trim()).toUpperCase();
					acceptCommand(command);
				} catch (IllegalArgumentException | NoSuchElementException iae) {
					printMessage(iae.getMessage());
				} catch (InvalidCommandException ice) {
					printMessage(ice.getMessage());
					printMessage(ice.getHelpMessage());
				} catch (Exception e) {
					printMessage(e.getMessage());
				}
			}
		}
	}

	public static void acceptCommand(String command) {

		String[] commandParams = (command.trim()).split("\\s+");
		String mainCommand = commandParams[0];

		String[] params = Arrays.copyOfRange(commandParams, 1, commandParams.length);

		switch (mainCommand) {
		case Constant.DISPLAY:
			vm.whatsAvailable();
			break;
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
			throw new InvalidCommandException(Constant.INVALID_COMMAND, Constant.COMMAND_HELP_MESSAGE);
		}

	}

	private static void printMessage(String message) {
		System.out.println(message);
	}
}
