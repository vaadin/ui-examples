package com.vaadin.invoice.editor;

import java.util.Random;

public enum Description {
    BILL("Hotel bill"), TICKETS("Flight tickets"), METRO("metro"), TAXI("Taxi"), LUNCH("Team lunch"), OFFSITE("Team offsite, meeting rooms etc.");

    private String stringRepresentation;

    private Description(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static String getRandomDescription() {
        Random random = new Random();
        return Description.values()[random.nextInt(values().length)].getStringRepresentation();
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}
