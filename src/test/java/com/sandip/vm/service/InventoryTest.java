package com.sandip.vm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sandip.vm.model.Product;

public class InventoryTest {
	private Inventory inventory;

	@Before
	public void setUp() throws IOException {
		inventory = new Inventory(4);
		inventory.initializeInventory();
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

	@Test(expected = IllegalArgumentException.class)
	public void testGetProductPriceInvalidSlot() {
		//Given
		int productSlot = 4;
		inventory.getProductPrice(productSlot);
	}
	
	@Test
	public void testGetProductPriceValidSlot() {
		//Given
		int productSlot = 2;
		//When
		double price = inventory.getProductPrice(productSlot);
		//Then
		assertEquals(3.5, price,0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetProductQuantityInvalidSlot() {
		//Given
		int productSlot = 4;
		inventory.getProductQuantity(productSlot);
	}
	
	@Test
	public void testGetProductQuantityValidSlot() {
		//Given
		int productSlot = 2;
		//When
		double price = inventory.getProductQuantity(productSlot);
		//Then
		assertEquals(3, price,0);
	}
	
	@Test
	public void testDesiredProduct() {
		//Given
		int productSlot = 2;
		//When
		Product product = inventory.desiredProduct(productSlot);
		assertNotNull(product);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDesiredProductInvalidSlot() {
		//Given
		int productSlot = 4;
		//When
		inventory.desiredProduct(productSlot);
	}
}
