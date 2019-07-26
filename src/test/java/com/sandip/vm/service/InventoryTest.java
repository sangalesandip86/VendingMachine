package com.sandip.vm.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sandip.vm.model.Product;

public class InventoryTest {
	private Inventory inventory;

	@Before
	public void setUp() throws IOException {
		inventory = new Inventory(4);
	}

	@Test
	public void testWhatsAvailable() {
		Product[] products = inventory.getProductSlots();
		assertEquals(4, products.length);
	}

	@Test(expected = IllegalStateException.class)
	public void testSetProductQuantityWhenPriceIsNotSpecified() {
		Product product = new Product("Coke", 0.0, 0);
		inventory.setProductQuantity(product, 1);
	}

	@Test
	public void testSetProductQuantityWhenPriceIsSpecified() {
		Product product = new Product("Coke", 1.0, 0);
		inventory.setProductQuantity(product, 1);
		assertEquals(1, product.getQuantity());
	}

	@Test
	public void setProductPrice() {
		Product product = new Product("Coke", 1.1, 1);
		inventory.setProductPrice(product, 2.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setProductPriceThrowExceptionIfProductSlotIsEmpty() {
		Product product = null;
		inventory.setProductPrice(product, 2.1);
	}

}
