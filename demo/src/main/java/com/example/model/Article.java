package com.example.model;

public class Article {

    private String name;
    private int quantity;
    private double unitPrice;
    private double dicount;

    public Article() {
    }

    public Article(String name, int quantity, double unitPrice, double dicount) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.dicount = dicount;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity
        ;
    }

    public void setQuantity(int quantity
    ) {
        this.quantity
         = quantity
        ;
    }

    public double getUnitPrice() {
        return unitPrice
        ;
    }

    public void setUnitPrice(double unitPrice
    ) {
        this.unitPrice
         = unitPrice
        ;
    }

    public double getDiscount() {
        return dicount;
    }

    public void setDiscount(double dicount) {
        this.dicount = dicount;
    }

    public double getGrossAmount() {
        return Calculator.multiply(quantity
        , unitPrice
        );
    }

    public double getDiscountedAmount() {
        return Calculator.applyDiscount(getGrossAmount(), dicount);
    }
}
