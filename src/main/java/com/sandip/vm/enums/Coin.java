package com.sandip.vm.enums;

public enum Coin {
	ONE_PENCE("One Pence", 1), TWO_PENCE("Two Pence", 2), FIVE_PENCE("Five Pence", 5), TEN_PENCE("TEN PENCE", 10),
	TWENTY_PENCE("Twenty Pence", 20), FIFTY_PENCE("Fifty Pence", 50), POUND("Pound", 100), TWO_POUND("Two Pound", 200);
	private int denom;
	private String name;

	private Coin(String name, int denom) {
		this.name = name;
		this.denom = denom;
	}

	public int getDenom() {
		return denom;
	}

	public String getName() {
		return name;
	}

	public static Coin valueOf(int denom) {
		for (Coin coin : values()) {
			if (coin.denom == denom) {
				return coin;
			}
		}
		throw new IllegalArgumentException("Invalid Denomination");
	}
	
}
