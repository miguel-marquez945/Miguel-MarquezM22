package com.example.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @JsonProperty("id")
    private String id;

    // En tu JSON suele venir como "articles"; por si acaso, admitimos "items"
    @JsonProperty("articles") @JsonAlias({"items"})
    private List<Article> articles;

    // Si tu JSON trae el tipo de cambio a USD, lo mapeamos; si no, se queda en 1.0
    @JsonProperty("usdRate") @JsonAlias({"rateUSD","usd_rate","dollarRate"})
    private double usdRate = 1.0;

    public Order() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Article> getArticles() { return articles; }
    public void setArticles(List<Article> articles) { this.articles = articles; }

    public double getUsdRate() { return usdRate; }
    public void setUsdRate(double usdRate) { this.usdRate = usdRate; }

    // Totales
    public double getGrossTotal() {
        if (articles == null) return 0.0;
        return articles.stream().mapToDouble(Article::grossAmount).sum();
    }

    public double getDiscountedTotal() {
        if (articles == null) return 0.0;
        return articles.stream().mapToDouble(Article::discountedAmount).sum();
    }

    // Conversión a USD (si el controller los usa)
    public double getGrossTotalUsd() { return getGrossTotal() * usdRate; }
    public double getDiscountedTotalUsd() { return getDiscountedTotal() * usdRate; }
}
