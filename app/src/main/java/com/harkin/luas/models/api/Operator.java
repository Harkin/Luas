package com.harkin.luas.models.api;

import com.harkin.luas.models.Line;

import java.util.List;

public class Operator {
    private final Line line;

    public Operator(Builder builder) {
        if (builder.routes != null && !builder.routes.isEmpty()) {
            line = Line.toLine(builder.routes.get(0).toUpperCase());
        } else {
            line = Line.UNKNOWN;
        }
    }

    public static class Builder {
        private List<String> routes;

        public Operator build() {
            return new Operator(this);
        }
    }

    public Line getLine() {
        return line;
    }
}
