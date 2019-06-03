package com.vaadin.invoice.editor;

import java.io.Serializable;

public class Invoice implements Serializable {
    private String product;
    private String description;
    private float price;
    private Currency currency;
    private int vat;
    private int amount;
    private Category category;
    private Boolean isOrderCompleted;
    private int total;

    public Invoice() {
        super();
    }

    public Invoice(String description, int price, Currency currency, int vat, int amount, Category category, Boolean isOrderCompleted, int total) {
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.vat = vat;
        this.amount = amount;
        this.category = category;
        this.isOrderCompleted = isOrderCompleted;
        this.total = total;
    }

    @Override
    public String toString() {
        return " product " + product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getOrderCompleted() {
        return isOrderCompleted;
    }

    public void setOrderCompleted(Boolean orderCompleted) {
        isOrderCompleted = orderCompleted;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public Invoice clone() {
        try {
            return (Invoice) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(
                    "The Invoices object could not be cloned.", e);
        }
    }
}
