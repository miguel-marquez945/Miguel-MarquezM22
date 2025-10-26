package com.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    @JsonProperty("name")
    @JsonAlias({"nombre"})
    private String nombre;

    @JsonProperty("quantity")
    @JsonAlias({"cantidad"})
    private int cantidad;

    @JsonProperty("price")
    @JsonAlias({"precio"})
    private double precio;

    @JsonProperty("discount")
    @JsonAlias({"descuento"})
    private double descuento;

    public Article() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public double getGrossAmount() { return cantidad * precio; }

    public double getDiscountedAmount() {
        return getGrossAmount() * (1.0 - (descuento / 100.0));
    }

    @Override
    public String toString() {
        return "Article{nombre='" + nombre + "', cantidad=" + cantidad +
               ", precio=" + precio + ", descuento=" + descuento + "}";
    }
}
