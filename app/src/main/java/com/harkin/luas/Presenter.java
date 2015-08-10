package com.harkin.luas;

import android.location.Location;
import android.location.LocationManager;

import com.harkin.luas.network.LuasApi;
import com.harkin.luas.network.models.Stop;
import com.harkin.luas.network.models.StopList;
import com.harkin.luas.network.models.Timetable;
import com.harkin.luas.network.models.Tram;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

public class Presenter {
    private final MainActivity activity;

    private final List<Stop> stops = new ArrayList<>();
    private Stop currentStop;

    public Presenter(MainActivity activity) {
        this.activity = activity;
    }

    public void loadStops() {
        LuasApi.getInstance().getStops(activity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStopsLoaded,
                        throwable -> {/* nom nom error */});
        //todo sensible error handling if stops are unavailable
    }

    public void refresh() {
        if (!stops.isEmpty()) {
            currentStop = findClosestStop(getLocation());
            LuasApi.getInstance().getTrams(currentStop.getShortName())
                    .map(this::createTramsList)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::displayLuasTimes,
                            throwable -> {/* nom nom error */});
            //todo retry grabbing trams if there's an error
        }
    }

    private void onStopsLoaded(StopList response) {
        stops.addAll(response.getStops());
        refresh();
    }

    private void displayLuasTimes(List<Tram> trams) {
        //todo sort due times
        activity.displayTimes(currentStop.getDisplayName(), trams);
    }

    private List<Tram> createTramsList(Timetable timetable) {
        List<Tram> trams = new ArrayList<>();
        trams.add(new Tram.Builder().withDueTime("Inbound").build());
        trams.addAll(timetable.getInboundTrams());
        trams.add(new Tram.Builder().withDueTime("Outbound").build());
        trams.addAll(timetable.getOutboundTrams());
        return trams;
    }

    @DebugLog private Stop findClosestStop(Location location) {
        Stop closestStop = stops.get(0);

        if (location != null) {
            float[] results = new float[3];
            float closestStopDistance = Float.MAX_VALUE;

            for (Stop s : stops) {
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), s.getLatitude(), s.getLongitude(), results);
                if (results[0] < closestStopDistance) {
                    closestStopDistance = results[0];
                    closestStop = s;
                }
            }
        }

        return closestStop;
    }

    @DebugLog private Location getLocation() {
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
            //TODO if no last known loc available, poll for current loc.
            bestLocation = null;
        }
//https://github.com/mcharmas/Android-ReactiveLocation <-- looks really nifty!
        return bestLocation;
    }
}
