package com.harkin.luas.models;

public enum Line {
    GREEN, RED, UNKNOWN;

    // like valueOf only with a default
    public static Line toLine(String enumString) {
        try {
            return valueOf(enumString);
        } catch (Exception ex) {
            // For error cases
            return UNKNOWN;
        }
    }
}
