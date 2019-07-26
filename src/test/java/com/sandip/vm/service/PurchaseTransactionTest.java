package com.sandip.vm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.model.Product;

public class PurchaseTransactionTest {

	private PurchaseTransaction purchaseTransaction;

	@Before
	public void setUp() {
		purchaseTransaction = new PurchaseTransaction();
	}

	@Test
	public void testDepositeCoin() {
		purchaseTransaction.depositeCoin(Coin.POUND, 1);
		assertEquals(new Integer(1), purchaseTransaction.getCoinsDeposited().get(Coin.POUND));
	}

	@Test
	public void testIsProductSelected() {
		// Given
		purchaseTransaction.setDesiredProduct(new Product("Coke", 1.5, 1));
		assertTrue(purchaseTransaction.isProductSelected());
	}

	@Test
	public void testIsSufficientFundAvailable() {
		// Given
		purchaseTransaction.setDesiredProduct(new Product("Coke", 1.5, 1));
		purchaseTransaction.depositeCoin(Coin.POUND, 1);
		purchaseTransaction.depositeCoin(Coin.FIFTY_PENCE, 1);
		assertTrue(purchaseTransaction.isSuffientFundAvailable());
	}

	@Test
	public void testDepositedAmountUpdatedCorrectly() {
		purchaseTransaction.depositeCoin(Coin.POUND, 1);
		assertEquals(100, purchaseTransaction.getAmountDeposited(), 0);
	}

	@Test
	public void testDepositedCoinForExitingCoins() {
		purchaseTransaction.depositeCoin(Coin.POUND, 1);
		purchaseTransaction.depositeCoin(Coin.POUND, 1);
		assertEquals(new Integer(2), purchaseTransaction.getCoinsDeposited().get(Coin.POUND));
	}

	@Test
	public void testGetPurchaseProductPrice() {
		purchaseTransaction.setDesiredProduct(new Product("Coke", 1.5, 1));
		assertEquals(1.5, purchaseTransaction.getPurchaseAmount(), 0);
	}
}
