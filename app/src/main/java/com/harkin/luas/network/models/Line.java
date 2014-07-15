package com.harkin.luas.network.models;

import java.util.Locale;

public enum Line {
    GREEN, RED, UNKNOWN;

    // like valueOf only with a default
    public static Line toLine(String enumString) {
        try {
            return valueOf(enumString.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException | NullPointerException ignoredx) {
            return UNKNOWN;
        }
    }
}
