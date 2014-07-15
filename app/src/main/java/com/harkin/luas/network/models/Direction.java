package com.harkin.luas.network.models;

import java.util.Locale;

public enum Direction {
    INBOUND, OUTBOUND, UNKNOWN;

    // like valueOf only with a default
    public static Direction toDirection(String enumString) {
        try {
            return valueOf(enumString.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException | NullPointerException ignored) {
            return UNKNOWN;
        }
    }
}
