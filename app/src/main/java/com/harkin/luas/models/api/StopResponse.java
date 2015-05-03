package com.harkin.luas.models.api;

import java.util.ArrayList;
import java.util.List;

public class StopResponse {
    private final List<Stop> stops = new ArrayList<>();

    public StopResponse() {
        this(new Builder());
    }

    public StopResponse(Builder builder) {
        if (builder.results != null) {
            for (Stop.Builder stopBuilder : builder.results) {
                stops.add(stopBuilder.build());
            }
        }
    }

    public static class Builder {
        private List<Stop.Builder> results;

        public Builder withStops(List<Stop.Builder> stops) {
            results = stops;
            return this;
        }

        public StopResponse build() {
            return new StopResponse(this);
        }
    }

    public List<Stop> getStops() {
        return stops;
    }
}
