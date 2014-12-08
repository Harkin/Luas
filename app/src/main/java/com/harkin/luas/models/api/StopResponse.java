package com.harkin.luas.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by henry on 15/07/2014.
 */
public class StopResponse {
    private String errorcode;
    private String errormessage;
    private int numberofresults;
    private String timestamp;
 @SerializedName("results")
    private List<Stop> stops;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public int getNumberofresults() {
        return numberofresults;
    }

    public void setNumberofresults(int numberofresults) {
        this.numberofresults = numberofresults;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}
