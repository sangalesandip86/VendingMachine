package com.sandip.vm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.exceptions.NotSufficientChangeException;

public class MoneyBank {

	public List<Coin> getSupportedCoins() {
		return supportedCoins;
	}

	private List<Coin> supportedCoins;

	private Integer totalBalance = 0;

	private static List<Coin> buildSupportedCoinsList(List<Double> coinsSupported) {
		List<Coin> coins = new ArrayList<>();
		coinsSupported.forEach(coinValue -> coins.add(Coin.valueOf((int) (coinValue * 100))));
		return coins;
	}

	private void decreaseTotalBalance(Integer amount) {
		totalBalance = totalBalance - amount;
	}

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

	public void setCoinQuantity(Coin coin, int quantity) {
		if (coinCountMap.containsKey(coin)) {
			decreaseTotalBalance(coin.getDenom() * coinCountMap.get(coin));
		}
		coinCountMap.put(coin, quantity);
		increaseTotalBalance(coin.getDenom() * quantity);
	}

	private void addCoinsAndUpdateBalance(Coin coin, Integer noOfCoins) {
		if (coinCountMap.containsKey(coin)) {
			coinCountMap.put(coin, coinCountMap.get(coin) + noOfCoins);
		} else {
			coinCountMap.put(coin, noOfCoins);
		}
		increaseTotalBalance(coin.getDenom() * noOfCoins);
	}

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

	public Integer getCointCount(Coin coin) {
		return coinCountMap.get(coin) != null ? coinCountMap.get(coin) : 0;
	}

	public void initializeCoinAndBalance() {
		if (supportedCoins != null) {
			supportedCoins.forEach(coin -> {
				coinCountMap.put(coin, 10);
				totalBalance = totalBalance + coin.getDenom() * 10;
			});
		}
	}

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
			throw new NotSufficientChangeException("Not Sufficient change for amount");
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

	public void removeCoinsFromBank(Map<Coin, Integer> refundCoinCount) {
		refundCoinCount.forEach(this::removeCoinsAndUpdateBalance);
	}

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
