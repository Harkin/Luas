package com.harkin.luas.models.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 15/07/2014.
 */
public class Stop {
    private String stopid;
    private String displaystopid;
    private String shortname;
    private String shortnamelocalized;
    private String fullname;
    private String fullnamelocalized;
    private double latitude;
    private double longitude;
    private String lastupdated;
    private List<Operator> operators = new ArrayList<Operator>();

    public String getStopid() {
        return stopid;
    }

    public void setStopid(String stopid) {
        this.stopid = stopid;
    }

    public String getDisplaystopid() {
        return displaystopid;
    }

    public void setDisplaystopid(String displaystopid) {
        this.displaystopid = displaystopid;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getShortnamelocalized() {
        return shortnamelocalized;
    }

    public void setShortnamelocalized(String shortnamelocalized) {
        this.shortnamelocalized = shortnamelocalized;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullnamelocalized() {
        return fullnamelocalized;
    }

    public void setFullnamelocalized(String fullnamelocalized) {
        this.fullnamelocalized = fullnamelocalized;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }
}
