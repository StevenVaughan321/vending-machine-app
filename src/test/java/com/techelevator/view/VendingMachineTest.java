package com.techelevator.view;

import com.techelevator.Items.Product;
import com.techelevator.Items.VendingMachine;
import org.junit.Test;
import java.util.LinkedHashMap;
import static org.junit.Assert.assertEquals;

public class VendingMachineTest {
    private final String FILE_PATH_LOCATION = "vendingmachine.csv";
        @Test
        public void purchaseProductTest() {
            VendingMachine vendingMachine = new VendingMachine(FILE_PATH_LOCATION);
            vendingMachine.setCustomerBalance(100);
            assertEquals(true, vendingMachine.purchaseProduct("A4"));
            for ( Product product : vendingMachine.getProducts()) {
                if (product.getSlotLocation().equals("A4")) {
                    assertEquals(4, product.getAmountRemaining());

                    assertEquals(true, product.isInStock(product));
                    product.setAmountRemaining(0);
                    assertEquals(false, product.isInStock(product));

                }
            }
        }
        @Test
        public void testEndTransaction() {

            VendingMachine vendingMachine = new VendingMachine(FILE_PATH_LOCATION);
            vendingMachine.setCustomerBalance(2.00);
            vendingMachine.endTransaction();
            assertEquals(0, Double.parseDouble(vendingMachine.getCustomerBalance()), .00001);
        }
        @Test
        public void testGetChange() {
            VendingMachine vendingMachine = new VendingMachine(FILE_PATH_LOCATION);
            LinkedHashMap<String, Integer> expectedChange = new LinkedHashMap<>();
            expectedChange.put("Quarters", 14);
            expectedChange.put("Dimes", 1);
            expectedChange.put("Nickels", 1);
            vendingMachine.setCustomerBalance(3.65);
            LinkedHashMap<String, Integer> change = new LinkedHashMap<>(vendingMachine.getChange());
            assertEquals(expectedChange, change);

        }
        @Test
        public void testMakeProductsFromFile() {
            VendingMachine vendingMachine = new VendingMachine(FILE_PATH_LOCATION);

            for (Product product : vendingMachine.getProducts()) {
                if (product.getSlotLocation().equals("D3")) {
                    assertEquals("Chiclets", product.getName());
                    assertEquals("Gum", product.getType());
                    assertEquals(.75, product.getPrice(), .000001);
                }
            }
        }
}
