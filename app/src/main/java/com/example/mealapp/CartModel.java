package com.example.mealapp;

/**
 * Class that defines the attributes of an item in the shopping cart.
 */
public class CartModel {
    private String name;
    private String amount;

    //class constructors
        //full constructor
    public CartModel(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }
        //empty constructor
    public CartModel() {}

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    //toStrings
        //toString with no ingredients for listview
    @Override
    public String toString() {
        return "CartModel{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
