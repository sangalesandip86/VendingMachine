package com.sandip.vm.service;

import java.util.ArrayList;
import java.util.List;

import com.sandip.vm.model.Product;

public class Inventory {

	private static final String PRODUCT_NOT_PRESENT_ON_SELECTED_SLOT = "Product not present on selected slot";

	private static final String ERROR_PLEASE_SET_PRODUCT_PRICE_BEFORE_SETTING_QUANTITY = "Error : Please set product price before setting quantity";

	private static final String ERROR_PRODUCT_SLOT_DOES_NOT_EXIST = "Error : Product slot does not exist";

	private Product[] productSlots;

	public Inventory(Integer numberOfSlots) {
		productSlots = new Product[numberOfSlots];
	}

	public Product desiredProduct(Integer index) {
		if (index > productSlots.length - 1) {
			throw new IllegalArgumentException("Invalid product slot");
		}
		return productSlots[index];
	}

	/**
	 * 
	 * @return
	 */
	public static List<Product> getInventoryItems() {
		ArrayList<Product> inventoryItems = new ArrayList<>();
		inventoryItems.add(new Product("Coke", 1.5, 3));
		inventoryItems.add(new Product("Crunchie", 1.7, 3));
		inventoryItems.add(new Product("Popcorn", 3.5, 3));
		inventoryItems.add(new Product("Chips", 1.2, 1));
		inventoryItems.add(new Product("KitKat", 0.7, 2));
		return inventoryItems;
	}

	public Double getProductPrice(Integer productSlot) {
		try {
			Product product = productSlots[productSlot];
			return product.getPrice();
		} catch (ArrayIndexOutOfBoundsException aib) {
			throw new IllegalArgumentException(ERROR_PRODUCT_SLOT_DOES_NOT_EXIST);
		} catch (NullPointerException e) {
			throw new IllegalStateException(PRODUCT_NOT_PRESENT_ON_SELECTED_SLOT);
		}
	}

	public Integer getProductQuantity(Integer productSlot) {
		try {
			Product product = productSlots[productSlot];
			return product.getQuantity();
		} catch (ArrayIndexOutOfBoundsException aib) {
			throw new IllegalArgumentException(ERROR_PRODUCT_SLOT_DOES_NOT_EXIST);
		} catch (NullPointerException e) {
			throw new IllegalStateException(PRODUCT_NOT_PRESENT_ON_SELECTED_SLOT);
		}
	}

	public Product[] getProductSlots() {
		return productSlots;
	}

	public void setProductPrice(Product product, Double price) {
		if (product != null) {
			product.setPrice(price);
		} else {
			throw new IllegalArgumentException(ERROR_PRODUCT_SLOT_DOES_NOT_EXIST);
		}

	}

	public void setProductQuantity(Product productQ, Integer quantity) {
		if (productQ != null) {
			if (productQ.getPrice() == 0.0) {
				throw new IllegalStateException(ERROR_PLEASE_SET_PRODUCT_PRICE_BEFORE_SETTING_QUANTITY);
			} else {
				productQ.setQuantity(quantity);
			}
		}
	}

	public void initializeInventory() {
		List<Product> globalInventory = getInventoryItems();
		int globalIndex = 0;
		for (int i = 0; i < productSlots.length; i++) {
			productSlots[i] = globalInventory.get(globalIndex);
			globalIndex = globalIndex + 1;
			if (globalIndex > globalInventory.size() - 1) {
				globalIndex = 0;
			}
		}
	}
}
