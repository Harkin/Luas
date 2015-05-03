package com.harkin.luas.models.api;

import com.harkin.luas.models.Line;

public class StopTimetable {
    private static final String LUAS = "LUAS";

    private final int dueTime;
    private final String displayTime;
    private final String origin;
    private final String destination;
    private final String direction;
    private final Line line;

    public StopTimetable(Builder builder) {
        displayTime = builder.duetime == null ? "" : builder.duetime;
        origin = builder.origin == null ? "" : builder.origin.replace(LUAS, "").trim();
        destination = builder.destination == null ? "" : builder.destination.replace(LUAS, "").trim();
        direction = builder.direction == null ? "" : builder.direction;
        line = builder.route == null ? Line.UNKNOWN : Line.toLine(builder.route.toUpperCase());

        dueTime = Integer.getInteger(displayTime, 0);
    }

    public static class Builder {
        private String duetime;
        private String destination;
        private String origin;
        private String direction;
        private String route;

        // for now I don't really care about the Irish names
//        private String destinationlocalized;
//        private String originlocalized;

        public StopTimetable build() {
            return new StopTimetable(this);
        }
    }

    public int getDueTime() {
        return dueTime;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDirection() {
        return direction;
    }

    public Line getLine() {
        return line;
    }
}
