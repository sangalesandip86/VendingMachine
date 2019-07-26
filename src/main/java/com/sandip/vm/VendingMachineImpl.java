package com.sandip.vm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.exceptions.InvalidMenuOptionException;
import com.sandip.vm.exceptions.NotFullPaidException;
import com.sandip.vm.exceptions.SoldOutException;
import com.sandip.vm.model.Product;
import com.sandip.vm.service.Inventory;
import com.sandip.vm.service.MoneyBank;
import com.sandip.vm.service.PurchaseTransaction;
import com.sandip.vm.service.Screen;

/**
 * 
 * @author sandip.p.sangale
 *
 */
public class VendingMachineImpl implements VendingMachine {

	private static final String SOLD_OUT = "Sold out";

	private static final String PRODUCT_SLOT_MUST_BE_VALID_POSITIVE_NUMBER = "Product slot must be valid positive number";

	private static final String INSUFFICIENT_FUNDS_PLEASE_FEED_MORE_MONEY = "Insufficient Funds. Please feed more money.";

	private static final String PLEASE_SELECT_A_PRODUCT_FOR_PURCHASE = "Please select a product for purchase.";

	private static final String INVALID_CHOICE = "Invalid choice";

	private static final String IS_NOT_A_VALID_NUMBER = " is not a valid number";

	private static final String INVALID_COIN_DENOMINATION = "Invalid coin denomination, Machine accepts only 10, 20, 50, 100, 200 PENCE denominations";

	private static final String SELECT_COMMAND_EXPECT_EXACT_1_PRODUCT_SLOT = "SELECT command expect exact 1 product slot";

	private static final VendingMachineImpl SINGLE_INSTANCE = new VendingMachineImpl();

	private static Inventory inventory;

	private static MoneyBank moneyBank;

	private static final PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
	private static final Screen screen = new Screen();

	/**
	 * Build Vending machine instance using numberOfSlots and coinsSupported list
	 * 
	 * @param numberOfSlots
	 * @param coinsSupported
	 * @return
	 */
	public static VendingMachineImpl getInstance(Integer numberOfSlots, List<Double> coinsSupported) {
		synchronized (VendingMachineImpl.class) {
			inventory = new Inventory(numberOfSlots);
			moneyBank = new MoneyBank(coinsSupported);
		}
		return SINGLE_INSTANCE;
	}

	private VendingMachineImpl() {
	}

	@Override
	public void cancel() {
		System.out.println(moneyBank.refundAnyOverpayment(purchaseTransaction.getAmountDeposited()));
		purchaseTransaction.resetPurchase();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public void collectItemAndChange() {
		if (!purchaseTransaction.isProductSelected()) {
			screen.showErrorMessage(PLEASE_SELECT_A_PRODUCT_FOR_PURCHASE);
		} else if (!purchaseTransaction.isSuffientFundAvailable()) {
			screen.showErrorMessage(INSUFFICIENT_FUNDS_PLEASE_FEED_MORE_MONEY);
			throw new NotFullPaidException(INSUFFICIENT_FUNDS_PLEASE_FEED_MORE_MONEY, 0);
		} else {
			finishPurchase();
		}
	}

	private Map<Coin, Integer> finishPurchase() {
		vend(purchaseTransaction.getSelectedProduct());
		moneyBank.acceptPayment(purchaseTransaction.getCoinsDeposited());
		screen.showPurchasedProductAndBalance(purchaseTransaction.getSelectedProduct(),
				purchaseTransaction.getRemainingAmount());
		Map<Coin, Integer> returnedCoins = moneyBank.refundAnyOverpayment(purchaseTransaction.getRemainingAmount());
		screen.showRefund(returnedCoins);
		purchaseTransaction.resetPurchase();
		return returnedCoins;
	}

	private Coin getCoinByMenu(int coinMenu) {
		if (coinMenu == 1) {
			return Coin.TWO_POUND;
		} else if (coinMenu == 2) {
			return Coin.POUND;
		} else if (coinMenu == 3) {
			return Coin.FIFTY_PENCE;
		} else if (coinMenu == 4) {
			return Coin.TWENTY_PENCE;
		} else if (coinMenu == 5) {
			return Coin.TEN_PENCE;
		} else {
			throw new InvalidMenuOptionException(INVALID_CHOICE);
		}
	}

	@Override
	public void initializeMoneyBank() {
		moneyBank.initializeCoinAndBalance();
	}

	@Override
	public void initializeInventory() {
		inventory.initializeInventory();
	}

	@Override
	public void maintenance() {
		int menuSelection = screen.showMaintenaceManu();
		switch (menuSelection) {
		case 1:
			Coin coin = getCoinByMenu(screen.showSetCoinCountMenu());
			int quantityToSet = screen.showCoinQuantityMenu(coin.getName());
			moneyBank.setCointCount(coin, quantityToSet);
			break;
		case 2:
			Coin coinType = getCoinByMenu(screen.showGetCoinCountMenu());
			int availableQuantity = moneyBank.getCointCount(coinType);
			screen.showCoinAndAvailableQuantity(coinType, availableQuantity);
			break;
		case 3:
			Product desiredProduct = inventory.desiredProduct(screen.showSelectProductSlotMenu());
			double desiredPrice = screen.showSetProductPriceMenu(desiredProduct);
			inventory.setProductPrice(desiredProduct, desiredPrice);
			screen.showProductPrice(desiredProduct);
			break;
		case 4:
			Product product = inventory.desiredProduct(screen.showSelectProductSlotMenu());
			screen.showProductPrice(product);
			break;
		case 5:
			Product productQ = inventory.desiredProduct(screen.showSelectProductSlotMenu());
			int desiredProductQ = screen
					.showSetProductQuantityMenu(inventory.desiredProduct(screen.showSelectProductSlotMenu()));
			inventory.setProductQuantity(productQ, desiredProductQ);
			screen.showProductQuantity(productQ);
			break;
		case 6:
			Product quantityOfproduct = inventory.desiredProduct(screen.showSelectProductSlotMenu());
			screen.showProductQuantity(quantityOfproduct);
			break;
		default:
			break;
		}
	}

	/**
	 * This methods re-initialise Inventory items and Coins quantity
	 */
	@Override
	public void reset() {
		initializeInventory();
		initializeMoneyBank();
	}

	/**
	 * Vend Desired product from vending machine also updates product quantity
	 * 
	 * @param desired
	 */
	private void vend(Product desired) {
		int quantity = desired.getQuantity();
		int updatedQuantity = quantity - 1;
		desired.setQuantity(updatedQuantity);
	}

	/**
	 * This method fetch all available product and display them on screen
	 */
	@Override
	public List<Product> whatsAvailable() {
		List<Product> products = Arrays.asList(inventory.getProductSlots());
		screen.showWhatsAvailable(products);
		return products;
	}

	@Override
	public void selectProduct(String[] productSlot) {
		isValidSelectCommandArguments(productSlot);
		Product product = inventory.desiredProduct(Integer.parseInt(productSlot[0]));
		purchaseTransaction.setDesiredProduct(product);
		if (!product.isAvailable()) {
			throw new SoldOutException(SOLD_OUT);
		} else {
			screen.showDesiredProductAndPrice(product);
		}
	}

	private boolean isValidSelectCommandArguments(String[] params) {
		if (params.length == 1) {
			try {
				Integer.parseInt(params[0]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(PRODUCT_SLOT_MUST_BE_VALID_POSITIVE_NUMBER);
			}
			return true;
		} else {
			throw new IllegalArgumentException(SELECT_COMMAND_EXPECT_EXACT_1_PRODUCT_SLOT);
		}
	}

	@Override
	public List<Coin> insertCoins(String[] insertedCoins) {
		List<Coin> depositedCoins = new ArrayList<>();
		for (String coinDenom : insertedCoins) {
			if (isValidCoin(coinDenom)) {
				depositedCoins.add(Coin.valueOf(Integer.parseInt(coinDenom)));
			} else {
				throw new IllegalArgumentException(INVALID_COIN_DENOMINATION);
			}
		}
		purchaseTransaction.depositeCoin(depositedCoins);
		return depositedCoins;
	}

	private boolean isValidCoin(String coinDenom) {
		try {
			List<Coin> coins = moneyBank.getSupportedCoins();
			return coins.stream().anyMatch(e -> e.getDenom() == Integer.parseInt(coinDenom));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(coinDenom + IS_NOT_A_VALID_NUMBER);
		}
	}

	@Override
	public void quit() {
		System.exit(0);
	}
}
