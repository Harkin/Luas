package com.harkin.luas.network.models;

public class Example {
    private final String a;

    public Example(Builder builder) {
        a = builder.a;
        builder.blah++;
    }

    public String getA() {
        return a;
    }


    public static class Builder {
        private String a;
        private int blah;
    }
}
