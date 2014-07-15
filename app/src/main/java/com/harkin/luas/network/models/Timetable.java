package com.harkin.luas.network.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private final String message;
    private final List<Tram> inboundTrams = new ArrayList<>();
    private final List<Tram> outboundTrams = new ArrayList<>();

    public Timetable() {
        this(new Builder());
    }

    public Timetable(Builder builder) {
        message = builder.message != null ? builder.message : "";

        if (builder.directions != null) {
            for (TramList.Builder tramListBuilder : builder.directions) {
                if (tramListBuilder != null) {
                    TramList tramList = tramListBuilder.build();
                    if (tramList.direction == Direction.INBOUND) {
                        inboundTrams.addAll(tramList.trams);
                    } else if (tramList.direction == Direction.OUTBOUND) {
                        outboundTrams.addAll(tramList.trams);
                    }
                }
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public List<Tram> getInboundTrams() {
        return inboundTrams;
    }

    public List<Tram> getOutboundTrams() {
        return outboundTrams;
    }

    @Root(name = "stopInfo", strict = false)
    public static class Builder {
        @Element(name = "message")
        private String message;

        @ElementList(name = "direction", inline = true)
        private List<TramList.Builder> directions;

        public Timetable build() {
            return new Timetable(this);
        }
    }

    private static class TramList {
        private final List<Tram> trams = new ArrayList<>();
        private final Direction direction;

        private TramList(Builder builder) {
            direction = Direction.toDirection(builder.direction);

            if (builder.trams != null) {
                for (Tram.Builder tramBuilder : builder.trams) {
                    if (tramBuilder != null) {
                        trams.add(tramBuilder.build());
                    }
                }
            }
        }

        @Root(name = "direction", strict = false)
        private static class Builder {
            @Attribute(name = "name")
            private String direction;

            @ElementList(name = "tram", inline = true)
            private List<Tram.Builder> trams;

            private TramList build() {
                return new TramList(this);
            }
        }
    }

}
