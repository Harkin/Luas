package com.harkin.luas.network.models;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

public class Tram {
    private final String dueTime;
    private final String destination;

    public Tram() {
        this(new Builder());
    }

    public Tram(Builder builder) {
        dueTime = builder.dueTime != null ? builder.dueTime : "";
        destination = builder.destination != null ? builder.destination : "";
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getDestination() {
        return destination;
    }

    @Root(name = "tram", strict = false)
    public static class Builder {
        @Attribute(name = "dueMins")
        private String dueTime;

        @Attribute(name = "destination")
        private String destination;

        public Builder withDueTime(String dueTime) {
            this.dueTime = dueTime;
            return this;
        }

        public Tram build() {
            return new Tram(this);
        }
    }
}
