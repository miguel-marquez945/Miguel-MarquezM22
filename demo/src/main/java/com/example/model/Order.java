package com.example.model;

import java.util.List;

public class Order {

    private String id;
    private List<Article> articles;

        public Order() {
    }
    
    public Order(String id, List<Article> articles) {
        this.id = id;
        this.articles = articles;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public double getGrossTotal() {
        double total = 0.0;
        for (Article article : articles) {
            total += article.getGrossAmount();
        }
        return total;
    }

    public double getDiscountedTotal() {
        double total = 0.0;
        for (Article article : articles) {
            total += article.getDiscountedAmount();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', total=" + getGrossTotal() + ", totalConDescuento=" + getDiscountedTotal() + "}";
    }
}
