package com.harkin.luas;

import android.location.Location;
import android.location.LocationManager;

import com.harkin.luas.api.LuasAPI;
import com.harkin.luas.models.Line;
import com.harkin.luas.models.api.Stop;
import com.harkin.luas.models.api.StopResponse;
import com.harkin.luas.models.api.StopTimetableResponse;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class Presenter {
    private final List<Stop> redline = new ArrayList<>();
    private final List<Stop> greenline = new ArrayList<>();

    private final MainActivity activity;

    private Stop currentStop;

    public Presenter(MainActivity activity) {
        this.activity = activity;
    }

    public void loadStops(){
        LuasAPI.getInstance().getStops(activity);
    }

    @Subscribe public void luasStops(StopResponse response) {
        for (Stop stop : response.getStops()) {
            if (stop.getLine() == Line.RED) {
                redline.add(stop);
            } else if (stop.getLine() == Line.GREEN) {
                greenline.add(stop);
            }
        }

        currentStop = findClosestStop(getLocation());
        refresh();
    }

    @Subscribe public void luasTimes(StopTimetableResponse response) {
        //todo sort due times
        activity.displayTimes(currentStop.getDisplayName(), response.getDueTimes());
    }

    public void refresh() {
        LuasAPI.getInstance().getLuasTimes(currentStop.getStopId());
    }

    private Stop findClosestStop(Location location) {
        float[] results = new float[3];
        float closestStopDistance = Float.MAX_VALUE;

        Stop closestStop = greenline.get(0);

        if (location != null) {
            for (Stop s : redline) {
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), s.getLatitude(), s.getLongitude(), results);
                if (results[0] < closestStopDistance) {
                    closestStopDistance = results[0];
                    closestStop = s;
                }
            }

            for (Stop t : greenline) {
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), t.getLatitude(), t.getLongitude(), results);
                if (results[0] < closestStopDistance) {
                    closestStopDistance = results[0];
                    closestStop = t;
                }
            }
        }

        return closestStop;
    }

    private Location getLocation() {
        LocationManager lm = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        Location gps = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location network = lm.getLastKnownLocation((LocationManager.NETWORK_PROVIDER));
        Location bestLocation;
        if (gps != null && network != null) {
            if (gps.getTime() > network.getTime()) {
                bestLocation = gps;
            } else {
                bestLocation = network;
            }
        } else if (gps != null) {
            bestLocation = gps;
        } else if (network != null) {
            bestLocation = network;
        } else {
            //TODO if no last know loc available, poll for current loc.
            bestLocation = null;
        }

        return bestLocation;
    }


    //Maybe? Is play services location the best one to use, I unno
    //Should probably abstract all location stuff to a seperate class
//    private boolean checkPlayServicesAvailable() {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        // If Google Play services is available
//        if (ConnectionResult.SUCCESS == resultCode) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
