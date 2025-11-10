package com.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    @JsonProperty("name")     @JsonAlias({"nombre"})
    private String name;

    @JsonProperty("quantity") @JsonAlias({"cantidad"})
    private int quantity;

    @JsonProperty("price")    @JsonAlias({"precio"})
    private double price;

    @JsonProperty("discount") @JsonAlias({"descuento"})
    private double discount;

    public Article() {}

    // Getters/Setters en inglés (no duplicar con español)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    // Cálculos usados por el controlador/vista
    public double grossAmount() { return quantity * price; }
    public double discountedAmount() { return grossAmount() * (1.0 - discount / 100.0); }
}
