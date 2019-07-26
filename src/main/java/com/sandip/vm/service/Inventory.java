package com.sandip.vm.service;

import java.util.ArrayList;
import java.util.List;

import com.sandip.vm.exceptions.VendingMachineException;
import com.sandip.vm.model.Product;

public class Inventory {

	private static final String ERROR_PLEASE_SET_PRODUCT_PRICE_BEFORE_SETTING_QUANTITY = "Error : Please set product price before setting quantity";

	private static final String ERROR_PRODUCT_SLOT_DOES_NOT_EXIST = "Error : Product slot does not exist";

	private Product[] productSlots;

	public Inventory(Integer numberOfSlots) {
		productSlots = new Product[numberOfSlots];
	}

	public void addProduct(Integer productSlot, Product product) {
		if (productSlots[productSlot] == null) {
			productSlots[productSlot] = product;
		} else {
			throw new VendingMachineException("Product slot is not empty");
		}
	}

	public void replaceProduct(Integer productSlot, Product product) {
		productSlots[productSlot] = product;

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public Product desiredProduct(Integer index) {
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
		Product product = productSlots[productSlot];
		if (product != null) {
			return product.getPrice();
		} else {
			throw new IllegalArgumentException(ERROR_PRODUCT_SLOT_DOES_NOT_EXIST);
		}
	}

	public Integer getProductQuantity(Integer productSlot) {
		Product product = productSlots[productSlot];
		if (product != null) {
			return product.getQuantity();
		} else {
			throw new IllegalArgumentException(ERROR_PRODUCT_SLOT_DOES_NOT_EXIST);
		}
	}

	public Product[] getProductSlots() {
		return productSlots;
	}

	public void removeProduct(Integer productSlot) {
		productSlots[productSlot] = null;
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
