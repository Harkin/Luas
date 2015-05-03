package com.harkin.luas.models.api;

import com.harkin.luas.models.Line;

import java.util.ArrayList;
import java.util.List;

public class Stop {
    private static final String LUAS = "LUAS";

    private final String stopId;
    private final String displayName;
    private final Line line;
    private final double longitude;
    private final double latitude;

    public Stop(Builder builder) {
        stopId = builder.stopid == null ? "" : builder.stopid;
        longitude = builder.longitude;
        latitude = builder.latitude;

        if (builder.operators != null && !builder.operators.isEmpty()) {
            line = builder.operators.get(0).build().getLine();
        } else {
            line = Line.UNKNOWN;
        }

        if (builder.fullname != null && !builder.fullname.isEmpty()) {
            displayName = builder.fullname.replace(LUAS, "").trim();
        } else if (builder.displaystopid != null && !builder.displaystopid.isEmpty()) {
            displayName = builder.displaystopid.replace(LUAS, "").trim();
        } else {
            displayName = "";
        }
    }

    public static class Builder {
        private String stopid;
        private String displaystopid;
        private String fullname;
        private double longitude;
        private double latitude;
        private List<Operator.Builder> operators = new ArrayList<>();

        public Stop build() {
            return new Stop(this);
        }
    }

    // region getters

    public String getStopId() {
        return stopId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Line getLine() {
        return line;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // endregion
}
