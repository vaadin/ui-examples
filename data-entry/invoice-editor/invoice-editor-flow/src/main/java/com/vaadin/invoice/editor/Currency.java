package com.vaadin.invoice.editor;

import java.util.Random;

public enum Currency {
    EUR("EUR", "€"), USD("USD", "$"), GBP("GBP", "£");

    private String stringRepresentation;

    private String symbol;

    private Currency(String stringRepresentation, String symbol) {
        this.stringRepresentation = stringRepresentation;
        this.symbol = symbol;
    }

    public static Currency getRandomCurrency() {
        Random random = new Random();
        return Currency.values()[random.nextInt(values().length)];
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}
