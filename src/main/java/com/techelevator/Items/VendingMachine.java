package com.techelevator.Items;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VendingMachine {
    private double customerBalance;
    private List<Product> products;

    public VendingMachine(String filePath) {
        products = new ArrayList<>();
        makeProductsFromFile(filePath);
        this.customerBalance = 0.0;
    }
    private void makeProductsFromFile(String filePath) {
            try (Scanner itemScanner = new Scanner(new File(filePath))) {
                while (itemScanner.hasNextLine()) {
                    String line = itemScanner.nextLine();
                    String[] data = line.split("\\|");
                    String slotLocation = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    String type = data[3];
                    Product product = new Product(slotLocation, name, price, type);
                    products.add(product);
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        public  void displayMenuItems() {
        for (Product product : products) {
            if (product.isInStock(product)) {
                System.out.println(product.slotLocation + "|" + product.getName() + "|" + product.getPrice() + "|" + product.getType() + "| Amount remaining: " + product.getAmountRemaining());
            } else System.out.println(product.slotLocation + "|" + product.getName() + "|" + product.getPrice() + "|" + product.getType() + "| Amount remaining: SOLD OUT!");
        }
        }
        public void testList() {

        }
        public Boolean purchaseProduct(String slotLocation) {
        boolean isProductPurchased = false;

            for (Product product : products) {
                if (product.getSlotLocation().equals(slotLocation)) {
                    if (product.getPrice() <= customerBalance && (product.getAmountRemaining() > 0)) {
                        product.purchase();
                        customerBalance = customerBalance - product.getPrice();
                        isProductPurchased = true;



                        return isProductPurchased;
                    } else if (product.getAmountRemaining() <= 0) {
                        System.out.println("That product is sold out");
                        return isProductPurchased;
                    } else if (product.getPrice() >= customerBalance) {
                        System.out.println("Please insert more money to purchase");
                        return isProductPurchased;
                    }
                }

            }
            System.out.println(System.lineSeparator() + "*** "  + slotLocation +  " is not a valid slot ***" + System.lineSeparator());
                return isProductPurchased;
        }
    public void customerFeedMoney(double customerFeedMoney) {
        customerBalance = customerBalance + customerFeedMoney;
    }
    public void endTransaction() {
        LinkedHashMap<String , Integer> change = getChange();
        setCustomerBalance(0);
        System.out.println("Your change is :");
        if (change.isEmpty()) {
            System.out.println("0.00");
        } else {
            for (Map.Entry<String, Integer> entry : change.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }

    }
    public String getProductName(String slotLocation) {
        String answer = "";
        for (Product product : products) {
            if (product.getSlotLocation().equals(slotLocation)) {
                answer = product.getName() + " " + product.getSlotLocation();
            }
        } return answer;
    }
    public double getProductPrice(String slotLocation) {
        double answer = 0;
        for (Product product : products) {
            if (product.getSlotLocation().equals(slotLocation)) {
                answer = product.getPrice();
            }
        } return answer;
    }
    public LinkedHashMap<String, Integer> getChange() {
        LinkedHashMap<String, Integer> coins = new LinkedHashMap<>();
        coins.put("Quarters", 25);
        coins.put("Dimes", 10);
        coins.put("Nickels", 5);
        coins.put("Pennies", 1);
        LinkedHashMap<String, Integer> change = new LinkedHashMap<>();
        double amountOwed =   customerBalance * 100;
        for (Map.Entry<String, Integer> entry : coins.entrySet()) {

            String coinName = entry.getKey();
            Integer coinValue = entry.getValue();
            int numOfCoins =  (int) amountOwed / coinValue;
            if (numOfCoins > 0) {
                change.put(coinName, numOfCoins);
                amountOwed -= numOfCoins * coinValue;

            }
        } return change;


    }



    public String getCustomerBalance() {
        return String.format("%.2f", customerBalance);
    }

    public void setCustomerBalance(double customerBalance) {
        this.customerBalance = customerBalance;
    }

    public List<Product> getProducts() {
        return products;
    }
}

