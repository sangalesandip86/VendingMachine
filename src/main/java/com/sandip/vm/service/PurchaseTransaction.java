package com.sandip.vm.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.model.Product;

public class PurchaseTransaction {

	private double purchaseAmount = 0L;

	private double amountDeposited = 0L;
	private Map<Coin, Integer> coinsDeposited = new EnumMap<>(Coin.class);
	private Product desiredProduct;

	public void depositeCoin(Coin coin, int quantity) {
		if (coinsDeposited.containsKey(coin)) {
			coinsDeposited.put(coin, coinsDeposited.get(coin) + quantity);
		} else {
			coinsDeposited.put(coin, quantity);
		}
		amountDeposited = amountDeposited + (coin.getDenom() * quantity);
	}

	public void depositeCoin(List<Coin> coins) {
		for (Coin coin : coins) {
			if (coinsDeposited.containsKey(coin)) {
				coinsDeposited.put(coin, coinsDeposited.get(coin) + 1);
			} else {
				coinsDeposited.put(coin, 1);
			}
			amountDeposited = amountDeposited + (coin.getDenom() * 1);
		}
	}

	public double getAmountDeposited() {
		return amountDeposited;
	}

	public Map<Coin, Integer> getCoinsDeposited() {
		return coinsDeposited;
	}

	public double getPurchaseAmount() {
		return purchaseAmount;
	}

	public double getRemainingAmount() {
		return amountDeposited - purchaseAmount * 100;
	}

	public Product getSelectedProduct() {
		return desiredProduct;
	}

	public boolean isProductSelected() {
		return desiredProduct != null;
	}

	public boolean isSuffientFundAvailable() {
		return purchaseAmount != 0 && amountDeposited >= purchaseAmount * 100;
	}

	public double requiredFund() {
		return purchaseAmount * 100 - amountDeposited;
	}

	public void resetPurchase() {
		coinsDeposited.clear();
		amountDeposited = 0L;
		purchaseAmount = 0L;
		desiredProduct = null;
	}

	public void setDesiredProduct(Product desiredProduct) {
		this.desiredProduct = desiredProduct;
		this.purchaseAmount = desiredProduct.getPrice();
	}
}
