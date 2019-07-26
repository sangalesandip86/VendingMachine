package com.sandip.vm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.exceptions.NotSufficientChangeException;

public class MoneyBankTest {

	private MoneyBank moneyBank;

	@Before
	public void setUp() {
		moneyBank = new MoneyBank(Arrays.asList(2.0, 1.0, 0.5, 0.2, 0.1));
	}

	@Test
	public void testGetCoinsCountExpectZeroIfCoinNotPresent() {
		// Given
		moneyBank.setCointCount(Coin.TWO_POUND, 0);
		// No coins available in machine
		// When
		Integer actualResult = moneyBank.getCointCount(Coin.TWO_POUND);
		// Then
		Integer expectedResult = 0;
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGetCoinsCorrectCount() {
		// Given
		moneyBank.initializeCoinAndBalance();
		// When
		Integer actualResult = moneyBank.getCointCount(Coin.TWO_POUND);
		// Then
		Integer expectedResult = 10;
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testAcceptCorrectNumberOfCoinsAndSetCoinCount() {
		// Given
		EnumMap<Coin, Integer> coinsDeposited = new EnumMap<Coin, Integer>(Coin.class);
		coinsDeposited.put(Coin.TWO_POUND, 2);
		coinsDeposited.put(Coin.POUND, 2);
		coinsDeposited.put(Coin.FIFTY_PENCE, 2);
		coinsDeposited.put(Coin.TWENTY_PENCE, 2);
		coinsDeposited.put(Coin.TEN_PENCE, 2);

		moneyBank.acceptPayment(coinsDeposited);
		// assertTrue(checkQuantity(coinsDeposited));
	}

	@Test(expected = NotSufficientChangeException.class)
	public void testRepaymentThrowNotSufficientChangeExceptionWhenNoCoinsAvailableInMachine() {
		// When
		moneyBank.refundAnyOverpayment(2.0);
	}

	@Test
	public void testRepaymentExpectLeastPossibleNumberOfcoinsForGivenAmount() {
		// When
		moneyBank.initializeCoinAndBalance();
		Map<Coin, Integer> returnedCoinsCountMap = moneyBank.refundAnyOverpayment(200.0);
		assertEquals(returnedCoinsCountMap.get(Coin.TWO_POUND), new Integer(1));
	}

	@Test
	public void testSetCoinCountLessThanCurrentQuantity() {
		// Given
		moneyBank.initializeCoinAndBalance();
		// When
		moneyBank.setCointCount(Coin.POUND, 2);
		// Then
		assertTrue(moneyBank.getCointCount(Coin.POUND).equals(2));
	}

	@Test
	public void testSetCoinCountGreaterThanCurrentQuantity() {
		// Given
		moneyBank.initializeCoinAndBalance();
		// When
		moneyBank.setCointCount(Coin.POUND, 12);
		// Then
		assertTrue(moneyBank.getCointCount(Coin.POUND).equals(12));
	}

	@Test
	public void testRefundAnyOverpayment() {
		moneyBank.initializeCoinAndBalance();
		Map<Coin, Integer> refundedCoins = moneyBank.refundAnyOverpayment(120.0);
		assertEquals(1, refundedCoins.get(Coin.POUND), 0.0);
		assertEquals(1, refundedCoins.get(Coin.TWENTY_PENCE), 0.0);
	}

	@Test
	public void testRefundAnyOverpayment1() {
		moneyBank.initializeCoinAndBalance();
		Map<Coin, Integer> refundedCoins = moneyBank.refundAnyOverpayment(1520.0);
		assertEquals(7, refundedCoins.get(Coin.TWO_POUND), 0.0);
		assertEquals(1, refundedCoins.get(Coin.POUND), 0.0);
		assertEquals(1, refundedCoins.get(Coin.TWENTY_PENCE), 0.0);
	}
}
