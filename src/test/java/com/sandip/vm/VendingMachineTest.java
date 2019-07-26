package com.sandip.vm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sandip.vm.enums.Coin;
import com.sandip.vm.model.Product;
import com.sandip.vm.service.Inventory;
import com.sandip.vm.service.PurchaseTransaction;
import com.sandip.vm.service.Screen;

public class VendingMachineTest {

	private static VendingMachine vm = VendingMachineImpl.getInstance(4, Arrays.asList(0.1, 0.2, 0.5, 1.0, 2.0));

	Screen screen = mock(Screen.class);
	Inventory inventory = mock(Inventory.class);
	PurchaseTransaction purchaseTransaction = mock(PurchaseTransaction.class);

	@BeforeClass
	public static void setUp() throws IOException {
		Product[] productSlots = new Product[3];
		productSlots[0] = new Product("Coke", 1.5, 1);
		productSlots[0] = new Product("Coke", 1.0, 1);
		productSlots[0] = new Product("Coke", 2.1, 1);
	}

	@Test
	public void testWhatsAvailable() {
		Product[] productSlots = new Product[3];
		productSlots[0] = new Product("Coke", 1.5, 1);
		productSlots[0] = new Product("Coke", 1.0, 1);
		productSlots[0] = new Product("Coke", 2.1, 1);

		doReturn(productSlots).when(inventory).getProductSlots();
		doNothing().when(screen).showWhatsAvailable(Arrays.asList(productSlots));
		List<Product> products = vm.whatsAvailable();
	}
	
	@Test
	public void testInsertCoins() {
		//Given
		String[] coinsArray = {"200", "100", "50", "20", "10"};
		//When
		List<Coin> coins = vm.insertCoins(coinsArray);
		assertEquals(5, coins.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertInvalidCoinDenom() {
		//Given
		String[] coinsArray = {"210"};
		//When
		vm.insertCoins(coinsArray);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertInvalidCoinDenomNegative() {
		//Given
		String[] coinsArray = {"-200"};
		//When
		vm.insertCoins(coinsArray);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertInvalidNumber() {
		//Given
		String[] coinsArray = {"200","S1"};
		//When
		vm.insertCoins(coinsArray);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSelectProduct() {
		//Given
		String[] slots = {""};
		//When
		vm.selectProduct(slots);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSelectProductMoreThanOneArgument() {
		//Given
		String[] slots = {"1 2"};
		//When
		vm.selectProduct(slots);
	}
	
	@Test
	public void testSelectProductValidSlot() {
		//Given
		String[] slots = {"0"};
		Product desiredProduct= new Product("Coke", 1.1, 1);
		when(inventory.desiredProduct(any(Integer.class))).thenReturn(desiredProduct);
		doNothing().when(purchaseTransaction).setDesiredProduct(desiredProduct);
		//When
		vm.selectProduct(slots);
	}
}
