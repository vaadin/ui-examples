package com.vaadin.invoice.editor;

import java.util.Random;

public enum Currency {
    EURO("eur"), USD("usd"), GBP("gbp");

    private String stringRepresentation;

    private Currency(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static Currency getRandomCurrency() {
        Random random = new Random();
        return Currency.values()[random.nextInt(values().length)];
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}
