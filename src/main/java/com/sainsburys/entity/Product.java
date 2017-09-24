package com.sainsburys.entity;

public class Product {
    private String title;
    private String kcalPer100g;
    private String unitPrice;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKcalPer100g() {
        return kcalPer100g;
    }

    public void setKcalPer100g(String kcalPer100g) {
        this.kcalPer100g = kcalPer100g;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", kcalPer100g='" + kcalPer100g + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
