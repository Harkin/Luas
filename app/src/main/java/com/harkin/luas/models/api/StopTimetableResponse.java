package com.harkin.luas.models.api;

import java.util.ArrayList;
import java.util.List;

public class StopTimetableResponse {
    private final List<StopTimetable> dueTimes = new ArrayList<>();
    private final String stopId;

    public StopTimetableResponse(Builder builder) {
        stopId = builder.stopid == null ? "" : builder.stopid;

        if (builder.results != null) {
            for (StopTimetable.Builder timetable : builder.results) {
                dueTimes.add(timetable.build());
            }
        }
    }

    public static class Builder {
        private String stopid;
        private List<StopTimetable.Builder> results;

        public StopTimetableResponse build() {
            return new StopTimetableResponse(this);
        }
    }

    public String getStopid() {
        return stopId;
    }

    public List<StopTimetable> getDueTimes() {
        return dueTimes;
    }
}
