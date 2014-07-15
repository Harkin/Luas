package com.harkin.luas.network.models;

import java.util.ArrayList;
import java.util.List;

public class StopList {
    private final List<Stop> stops = new ArrayList<>();

    public StopList() {
        this(new Builder());
    }

    public StopList(Builder builder) {
        if (builder.stations != null) {
            for (Stop.Builder stopBuilder : builder.stations) {
                if (stopBuilder != null) {
                    stops.add(stopBuilder.build());
                }
            }
        }
    }

    public List<Stop> getStops() {
        return stops;
    }

    public static class Builder {
        private List<Stop.Builder> stations;

        public StopList build() {
            return new StopList(this);
        }
    }
}
