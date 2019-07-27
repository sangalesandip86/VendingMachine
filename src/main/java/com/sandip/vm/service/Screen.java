package com.sandip.vm.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sandip.vm.constants.Constant;
import com.sandip.vm.enums.Coin;
import com.sandip.vm.model.Product;

/**
 * This class responsible for printing messages and scanner related operation
 * 
 * @author sandip.p.sangale
 *
 */
public class Screen {

	private static final String PRODUCT_HEADER_PRINT_FORMAT = "%-10s %-20s %15s %15s %n";

	private static Scanner scanner = new Scanner(System.in);

	private void printMaintenanceManu() {
		printMessage(Constant.SPACE);
		printMessage("1) Set Coins quantity");
		printMessage("2) Get Coins quantity");
		printMessage("3) Set Quantity of product slot ");
		printMessage("4) Get Quantity of product slot ");
		printMessage("5) Set Price of product slot ");
		printMessage("6) Get Price of product slot ");
		printMessage("7) Return Main Menu");
		printMessage(Constant.CHOICE);
		printMessage(Constant.SPACE);
	}

	/**
	 * Print string to console
	 * 
	 * @param message
	 */
	public void printMessage(String message) {
		System.out.println(message);
	}

	public int showCoinQuantityMenu(String name) {
		printMessage("Quantity of " + name + " coins >> ");
		return scanner.nextInt();
	}

	public void showDesiredProductAndPrice(Product desiredProduct) {
		DecimalFormat df = new DecimalFormat("#.00");
		printMessage(Constant.SPACE);
		printMessage(
				"You are purchasing " + desiredProduct.getName() + " for Â£" + df.format(desiredProduct.getPrice()));
		printMessage(Constant.SPACE);
	}

	/**
	 * 
	 * @param message
	 */
	public void showErrorMessage(String message) {
		printMessage(Constant.SPACE);
		printMessage("ERROR: " + message);
		printMessage(Constant.SPACE);
	}

	public int showCoinTypeMenu() {
		printMessage(Constant.SPACE);
		printMessage("Select Coin Type");
		printMessage("1) £2");
		printMessage("2) £1");
		printMessage("3) £0.5");
		printMessage("4) £0.2");
		printMessage("5) £0.1");
		printMessage(Constant.CHOICE);
		return scanner.nextInt();
	}

	public int showMaintenaceManu() {

		int menuSelection = 0;
		printMessage("Enter Security Code >> ");
		Integer password = scanner.nextInt();
		if (password.equals(1234)) {
			printMaintenanceManu();
			menuSelection = scanner.nextInt();
		} else {
			printMessage("ERROR: Please enter correct security code");
			showMaintenaceManu();
		}
		return menuSelection;
	}

	public void showPurchasedProductAndBalance(Product desiredProduct, Double amount) {
		DecimalFormat df = new DecimalFormat("#.00");
		printMessage(Constant.SPACE);
		printMessage("You have purchased : " + desiredProduct.getName());
		printMessage("Account Balance : £" + df.format(amount / 100));
		printMessage(Constant.SPACE);
	}

	public void showRefund(Map<Coin, Integer> returnedCoins) {
		if (!returnedCoins.isEmpty()) {
			printMessage(Constant.SPACE);
			printMessage("Please Collect your change");
			returnedCoins.forEach((key, value) -> printMessage(key + " : " + value));
			printMessage("Thanks for using our service");
			printMessage(Constant.SPACE);
		} else {
			printMessage(Constant.SPACE);
			printMessage("Thanks for using our service");
			printMessage(Constant.SPACE);
		}
	}

	public int showSelectProductSlotMenu() {
		printMessage("--Select Product--");
		printMessage("Enter Slot # >> ");
		return scanner.nextInt();
	}

	public void showWhatsAvailable(List<Product> products) {
		printMessage(Constant.SPACE);
		printMessage("--VENDING MACHINE ITEMS--");
		printMessage(Constant.SPACE);
		System.out.printf(PRODUCT_HEADER_PRINT_FORMAT, "SLOT", "PRODUCT", "PRICE", "QUANTITY");
		System.out.printf(PRODUCT_HEADER_PRINT_FORMAT, "----", "-------", "-----", "--------");
		int slotIndex = 0;
		for (Product product : products) {
			if (product.getQuantity() > 0) {
				String name = product.getName();
				Integer quantity = product.getQuantity();
				Double price = product.getPrice();
				DecimalFormat df = new DecimalFormat("0.00");
				System.out.printf(PRODUCT_HEADER_PRINT_FORMAT, slotIndex, name, "£" + df.format(price), quantity);
			}
			slotIndex++;
		}
	}
/**
 * 
 * @return
 */
	public int showSetCoinCountMenu() {
		return showCoinTypeMenu();
	}
/**
 * 
 * @return
 */
	public int showGetCoinCountMenu() {
		return showCoinTypeMenu();
	}
/**
 * 
 * @param product
 * @return
 */
	public double showSetProductPriceMenu(Product product) {
		printMessage("Enter new price for " + product + " : >> ");
		return scanner.nextDouble();
	}
/**
 * 
 * @param coinType
 * @param availableQuantity
 */
	public void showCoinAndAvailableQuantity(Coin coinType, int availableQuantity) {
		printMessage(Constant.SPACE);
		printMessage("Available Quantity of " + coinType.getName() + " is " + availableQuantity);
		printMessage(Constant.SPACE);
	}

	/**
	 * 
	 * @param product
	 */
	public void showProductPrice(Product product) {
		DecimalFormat df = new DecimalFormat("#.00");
		printMessage(Constant.SPACE);
		printMessage("Price of " + product.getName() + " is Â£" + df.format(product.getPrice()));
		printMessage(Constant.SPACE);
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	public int showSetProductQuantityMenu(Product product) {
		printMessage("Enter new quantity for " + product.getName() + "  >> ");
		return scanner.nextInt();
	}

	/**
	 * 
	 * @param quantityOfproduct
	 */
	public void showProductQuantity(Product quantityOfproduct) {
		printMessage(Constant.SPACE);
		printMessage("Available quantity of " + quantityOfproduct.getName() + " is " + quantityOfproduct.getQuantity());
		printMessage(Constant.SPACE);

	}
}
