package com.techelevator.Items;

import java.util.List;

public class Product {

    static final int STARTING_SNACK_AMOUNT = 5;
    String slotLocation;
    private String type;
    private int amountRemaining;
    private Double price;

    private String name;


    public Product(String slotLocation, String productName, Double price, String type) {
        this.slotLocation = slotLocation;
        this.name = productName;
        this.price = price;
        this.amountRemaining = STARTING_SNACK_AMOUNT;
        this.type = type;
    }
    public Product(List<Product> products) {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmountRemaining() {

        return amountRemaining;
    }

    public void setAmountRemaining(int amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public void purchase() {
        this.amountRemaining = amountRemaining - 1;
    }
    public Boolean isInStock(Product product) {
        return product.getAmountRemaining() > 0;
    }
}

