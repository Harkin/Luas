package com.harkin.luas.network.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;


public class StopList {
    private final List<Line> lines = new ArrayList<>();

    public StopList() {
        this(new Builder());
    }

    private StopList(Builder builder) {
        if (builder.lines != null) {
            for (Line.Builder lineBuilder : builder.lines) {
                if (lineBuilder != null) {
                    lines.add(lineBuilder.build());
                }
            }
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    @Root
    public static class Builder {
        @ElementList(name = "old_stops", inline = true, entry = "line", type = Line.Builder.class)
        private List<Line.Builder> lines;

        public StopList build() {
            return new StopList(this);
        }
    }
}
