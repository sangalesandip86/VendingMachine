package com.sandip.vm;

import java.io.IOException;
import java.util.List;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.model.Product;

public interface VendingMachine {
	/**
	 * Cancel current transaction and refund inserted coins if any.
	 */
	public void cancel();

	/**
	 * This method validates product prices and inserted amount and vend item and
	 * refund coins
	 */
	public void collectItemAndChange();

	/**
	 * Initialise Vending machine Money bank with 10 coins each type
	 */
	public void initializeMoneyBank();

	/**
	 * Initialise Vending machine inventory with products
	 */
	public void initializeInventory();

	/**
	 * Accept Array of coins, validate coins and deposit into machine
	 * 
	 * @param insertedCoins
	 * @return
	 */
	public List<Coin> insertCoins(String[] insertedCoins);

	public void maintenance();

	public void reset() throws IOException;

	public void selectProduct(String[] productSlot);

	public List<Product> whatsAvailable();

	public void quit();
}