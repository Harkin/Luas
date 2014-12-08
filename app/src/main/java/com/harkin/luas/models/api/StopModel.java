package com.harkin.luas.models.api;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by henry on 19/07/2014.
 */
@Table(name = "Stops")
public class StopModel extends Model {
    @Column(name = "stopid")
    private String stopid;

    @Column(name = "displaystopid")
    private String displaystopid;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "lastupdated")
    private String lastupdated;

    @Column(name = "route")
    private String route;

    public StopModel() {
        super();
    }

    public StopModel(String stopid, String displayId, double latitude, double longitude, String lastupdated, String route) {
        this.stopid = stopid;
        this.displaystopid = displayId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastupdated = lastupdated;
        this.route = route.toLowerCase();
    }

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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
