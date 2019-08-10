package com.harkin.luas.network.models;

import org.simpleframework.xml.Attribute;

public class Stop {
    private final String shortName;
    private final String displayName;
    private final double longitude;
    private final double latitude;

    public Stop() {
        this(new Builder());
    }

    private Stop(Builder builder) {
        shortName = builder.abrev != null ? builder.abrev : "";
        displayName = builder.pronunciation != null ? builder.pronunciation : "";

        longitude = builder.longitude;
        latitude = builder.lat;
    }

    // region getters

    public String getShortName() {
        return shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    // endregion

    static class Builder {
        @Attribute
        private String abrev;
        @Attribute
        private String pronunciation;

        @Attribute(name = "long")
        private double longitude;
        @Attribute
        private double lat;

        @Attribute
        private int isParkRide;
        @Attribute
        private int isCycleRide;

        Stop build() {
            return new Stop(this);
        }
    }
}
