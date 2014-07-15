package com.harkin.luas.network.models;

public class Stop {
    private final String shortName;
    private final String displayName;
    private final String displayIrishName;
    private final Line line;
    private final double longitude;
    private final double latitude;

    public Stop() {
        this(new Builder());
    }

    public Stop(Builder builder) {
        shortName = builder.shortName != null ? builder.shortName : "";
        displayName = builder.displayName != null ? builder.displayName : "";
        displayIrishName = builder.displayIrishName != null ? builder.displayIrishName : "";
        line = Line.toLine(builder.line);
        if (builder.coordinates != null) {
            longitude = builder.coordinates.getLongitude();
            latitude = builder.coordinates.getLatitude();
        } else {
            //todo maybe more sensible defaults here
            longitude = 0;
            latitude = 0;
        }
    }

    // region getters

    public String getShortName() {
        return shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayIrishName() {
        return displayIrishName;
    }

    public Line getLine() {
        return line;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    // endregion

    public static class Builder {
        private String shortName;
        private String displayName;
        private String displayIrishName;
        private String line;
        private Coordinates coordinates;

        public Stop build() {
            return new Stop(this);
        }
    }

    private static class Coordinates {
        private double latitude;
        private double longitude;

        private double getLatitude() {
            return latitude;
        }

        private double getLongitude() {
            return longitude;
        }
    }
}
