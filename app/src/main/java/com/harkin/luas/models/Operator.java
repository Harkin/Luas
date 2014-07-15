package com.harkin.luas.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 15/07/2014.
 */
public class Operator {
    private String name;
    private List<String> routes = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes( List<String> routes ) {
        this.routes = routes;
    }
}
