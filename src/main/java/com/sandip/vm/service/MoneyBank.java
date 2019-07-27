package com.sandip.vm.service;

/**
 * This class is responsible for coins and machine balance related operation
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.sandip.vm.constants.Constant;
import com.sandip.vm.enums.Coin;
import com.sandip.vm.exceptions.NotSufficientChangeException;

/**
 * 
 * @author sandip.p.sangale
 *
 */
public class MoneyBank {

	public List<Coin> getSupportedCoins() {
		return supportedCoins;
	}

	/**
	 * Holds list of supported coins in this machine
	 */
	private List<Coin> supportedCoins;
	/**
	 * This variable used to maintain total balance, It store balance in PENCE
	 * denomination
	 */
	private Integer totalBalance = 0;

	private static List<Coin> buildSupportedCoinsList(List<Double> coinsSupported) {
		List<Coin> coins = new ArrayList<>();
		coinsSupported.forEach(coinValue -> coins.add(Coin.valueOf((int) (coinValue * 100))));
		return coins;
	}

	private void decreaseTotalBalance(Integer amount) {
		totalBalance = totalBalance - amount;
	}

	/**
	 * 
	 * @param coinsSupported
	 */
	public MoneyBank(List<Double> coinsSupported) {
		this.supportedCoins = buildSupportedCoinsList(coinsSupported);
	}

	private void increaseTotalBalance(Integer amount) {
		totalBalance = totalBalance + amount;
	}

	private Map<Coin, Integer> coinCountMap = new EnumMap<>(Coin.class);

	public void acceptPayment(Map<Coin, Integer> coinsDeposited) {
		coinsDeposited.forEach(this::addCoinsAndUpdateBalance);
	}

	/**
	 * Set total coin quantity
	 * 
	 * @param coin
	 * @param quantity
	 */
	public void setCoinQuantity(Coin coin, int quantity) {
		if (coinCountMap.containsKey(coin)) {
			decreaseTotalBalance(coin.getDenom() * coinCountMap.get(coin));
		}
		coinCountMap.put(coin, quantity);
		increaseTotalBalance(coin.getDenom() * quantity);
	}

	/**
	 * 
	 * @param coin
	 * @param noOfCoins
	 */
	private void addCoinsAndUpdateBalance(Coin coin, Integer noOfCoins) {
		if (coinCountMap.containsKey(coin)) {
			coinCountMap.put(coin, coinCountMap.get(coin) + noOfCoins);
		} else {
			coinCountMap.put(coin, noOfCoins);
		}
		increaseTotalBalance(coin.getDenom() * noOfCoins);
	}

	/**
	 * 
	 * @param coins
	 */
	public synchronized void addCoinsIntoMachine(List<Coin> coins) {
		coins.forEach(coin -> addCoinsAndUpdateBalance(coin, 1));
	}

	private Integer coinChange(Map<Coin, Integer> refundCoinCountMap, Integer remainder, Coin coin) {
		Integer coinsCount = remainder / coin.getDenom();
		if (coinCountMap.containsKey(coin) && coinsCount <= coinCountMap.get(coin) && coinsCount > 0) {
			remainder = remainder - (coinsCount * coin.getDenom());
			refundCoinCountMap.put(coin, coinsCount);
		}
		return remainder;
	}

	/**
	 * 
	 * @param coin
	 * @return
	 */
	public Integer getCointCount(Coin coin) {
		return coinCountMap.get(coin) != null ? coinCountMap.get(coin) : 0;
	}

	/**
	 * 
	 */
	public void initializeCoinAndBalance() {
		if (supportedCoins != null) {
			supportedCoins.forEach(coin -> {
				coinCountMap.put(coin, 10);
				totalBalance = totalBalance + coin.getDenom() * 10;
			});
		}
	}

	/**
	 * This method calculated number of coins to return for remaining amount
	 * 
	 * refundAmount = £5.2 (£5.2 = 520 PENCE) 2 coins of £2 is possible combination
	 * (also checking that many coins available in machine) refundAmount =
	 * refundAmount - 200 * 2 (£2 = 200) For remaining amount try with next highest
	 * coin, and repeat same process
	 * 
	 * @param amount
	 * @return
	 */
	public Map<Coin, Integer> refundAnyOverpayment(Double amount) {
		Map<Coin, Integer> refundCoinCount = new EnumMap<>(Coin.class);

		// Convert amount into pence e.g £1 = 100
		Integer remainder = amount.intValue();
		Collections.sort(supportedCoins, Comparator.comparing(Coin::getDenom).reversed());
		for (Coin coin : supportedCoins) {
			if (remainder != 0) {
				remainder = coinChange(refundCoinCount, remainder, coin);
				if (remainder == 0)
					break;
			}
		}
		if (remainder > 0) {
			throw new NotSufficientChangeException(Constant.NOT_SUFFICIENT_CHANGE_FOR_AMOUNT);
		}
		removeCoinsFromBank(refundCoinCount);
		return refundCoinCount;
	}

	private void removeCoinsAndUpdateBalance(Coin coin, Integer noOfCoins) {
		if (coinCountMap.containsKey(coin)) {
			coinCountMap.put(coin, coinCountMap.get(coin) - noOfCoins);
			decreaseTotalBalance(coin.getDenom() * noOfCoins);
		}
	}

	/**
	 * 
	 * @param refundCoinCount
	 */
	private void removeCoinsFromBank(Map<Coin, Integer> refundCoinCount) {
		refundCoinCount.forEach(this::removeCoinsAndUpdateBalance);
	}

	/**
	 * 
	 * @param coinType
	 * @param quantity
	 */
	public void setCointCount(Coin coinType, Integer quantity) {
		if (coinCountMap.containsKey(coinType)) {
			Integer currentCount = coinCountMap.get(coinType);
			if (quantity - currentCount > 0) {
				coinCountMap.put(coinType, quantity);
				increaseTotalBalance(coinType.getDenom() * quantity - currentCount);
			} else if (quantity - currentCount < 0) {
				coinCountMap.put(coinType, quantity);
				decreaseTotalBalance(coinType.getDenom() * currentCount - quantity);
			}
		} else {
			coinCountMap.put(coinType, quantity);
			increaseTotalBalance(coinType.getDenom() * quantity);
		}
	}

}
