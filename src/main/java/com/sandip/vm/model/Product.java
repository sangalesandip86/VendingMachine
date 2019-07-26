package com.sandip.vm.model;

import com.sandip.vm.exceptions.VendingMachineException;

/**
 * Class for items in the vending machine
 */
public class Product {

	// Exception message for when the price is less than zero
	private static final String PRICE_LESS_THAN_ZERO_MESSAGE = "Price cannot be less than zero";

	private static final String QUANTITY_LESS_THAN_ONE_MESSAGE = "Quantity cannot be less than one";

	// The name of the item
	private String name;

	// The price of the item
	private double price;

	private int quantity;

	/**
	 * Constructor which fills in the name and price of the item Precondition: price
	 * argument >= 0 Postcondition: The name and price of the item is set to be the
	 * values in the arguments
	 * 
	 * @param name  The name of the item
	 * @param price The price of the item
	 * @throws VendingMachineException Thrown if price is less than zero
	 */
	public Product(String name, double price, int quantity) {
		this.name = name;
		if (price < 0) {
			throw new VendingMachineException(PRICE_LESS_THAN_ZERO_MESSAGE);
		} else {
			this.price = price;
		}

		if (quantity < 0) {
			throw new VendingMachineException(QUANTITY_LESS_THAN_ONE_MESSAGE);
		} else {
			this.quantity = quantity;
		}
	}

	/**
	 * Gets the name of the vending machine item
	 * 
	 * @return The string corresponding to the name of the vending machine item
	 *         Postcondition: the actual name of the item is returned
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the price of the vending machine item
	 * 
	 * @return The price of the vending machine item in dollars Postcondition: The
	 *         actual price of the item is returned
	 */
	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public boolean isAvailable() {
		return quantity > 0;

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
	}
}