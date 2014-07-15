package com.harkin.luas.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 14/07/2014.
 */
public class LuasTimeResponse {
    private String errorcode;
    private String errormessage;
    private int numberofresults;
    private String stopid;
    private String timestamp;
    private List<Luas> results = new ArrayList<Luas>();

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

    public String getStopid() {
        return stopid;
    }

    public void setStopid(String stopid) {
        this.stopid = stopid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Luas> getResults() {
        return results;
    }

    public void setResults(List<Luas> results) {
        this.results = results;
    }
}
