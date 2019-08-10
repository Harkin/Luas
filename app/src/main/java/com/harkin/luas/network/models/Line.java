package com.harkin.luas.network.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private final List<Stop> stops = new ArrayList<>();
    private final String name;

    public Line() {
        this(new Line.Builder());
    }

    public Line(Line.Builder builder) {
        if (builder.stops != null) {
            for (Stop.Builder stopBuilder : builder.stops) {
                if (stopBuilder != null) {
                    stops.add(stopBuilder.build());
                }
            }
        }
        name = builder.name != null ? builder.name : "";
    }

    public List<Stop> getStops() {
        return stops;
    }

    public String getName() {
        return name;
    }

    @Root
    static class Builder {
        @ElementList(inline = true, entry = "stop", type = Stop.Builder.class)
        private List<Stop.Builder> stops;
        @Attribute
        private String name;

        Line build() {
            return new Line(this);
        }
    }
}
