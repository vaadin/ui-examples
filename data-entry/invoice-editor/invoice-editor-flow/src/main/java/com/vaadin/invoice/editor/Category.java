package com.vaadin.invoice.editor;

import java.util.Random;

public enum Category {
    PERSONAL("Personal"), TEAM("Team expenses");

    private String stringRepresentation;

    private Category(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static Category getRandomCategory() {
        Random random = new Random();
        return Category.values()[random.nextInt(values().length)];
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}
