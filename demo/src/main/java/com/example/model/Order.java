package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @JsonProperty("id")
    private String id;

    // El JSON trae "articles"; opcionalmente aceptamos "items" también.
    @JsonProperty("articles")
    @JsonAlias({"items"})
    private List<Article> articulos = new ArrayList<>();

    public Order() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Article> getArticulos() { return articulos; }
    public void setArticulos(List<Article> articulos) {
        this.articulos = (articulos == null) ? new ArrayList<>() : articulos;
    }

    public double getGrossTotal() {
        double total = 0.0;
        for (Article a : articulos) if (a != null) total += a.getGrossAmount();
        return total;
    }

    public double getDiscountedTotal() {
        double total = 0.0;
        for (Article a : articulos) if (a != null) total += a.getDiscountedAmount();
        return total;
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', items=" + (articulos == null ? 0 : articulos.size()) + "}";
    }
}
