package com.harkin.luas;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.gson.Gson;
import com.harkin.luas.api.LuasAPI;
import com.harkin.luas.models.api.Stop;
import com.harkin.luas.models.api.StopResponse;
import com.harkin.luas.services.GeofenceIntentService;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GeoFences extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener,
        OnAddGeofencesResultListener {

    private List<Geofence> geofences = new ArrayList<Geofence>();
    private boolean workInProgress = false;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fences);


        LuasAPI.getInstance(getApplicationContext()).getStops(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    StopResponse stopResponse = gson.fromJson(response.body().charStream(), StopResponse.class);

                    Geofence.Builder builder = new Geofence.Builder();

                    float closest = 1000000000;
                    Stop a = null;
                    Stop b = null;
                    for (Stop stop : stopResponse.getStops()) {
                        Geofence g = builder
                                .setRequestId(stop.getFullname())
                                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                                .setCircularRegion(
                                        stop.getLatitude(),
                                        stop.getLongitude(),
                                        100)
                                .build();
                        geofences.add(g);

                        for (Stop s : stopResponse.getStops()) {
                            float[] result = new float[3];
                            if (!stop.getFullname().equals(s.getFullname())) {
                                Location.distanceBetween(stop.getLatitude(), stop.getLongitude(), s.getLatitude(), s.getLongitude(), result);
                                if (result[0] < closest) {
                                    closest = result[0];
                                    a = stop;
                                    b = s;
                                }
                            }
                        }
                    }
                    addGeofences();
                    Log.d("GEOFENCES", "Closest stops are " + a.getFullname() + " and " + b.getFullname() + " at " + closest);

                }
            }
        });
    }

    private void addGeofences() {
        if (ConnectionResult.SUCCESS != GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext())) {
            Log.d("GEOFENCES", "Google play services unavailable. Abort, ABORT");
            return;
        }

        mLocationClient = new LocationClient(this, this, this);
        if (!workInProgress) {
            workInProgress = true;
            mLocationClient.connect();
        } else {
            Log.d("GEOFENCES", "Work in progress, move along");
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent(this, GeofenceIntentService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mLocationClient.addGeofences(geofences, pIntent, this);
    }

    @Override
    public void onDisconnected() {
        Log.d("GEOFENCES", "disconnected wtf");
        workInProgress = false;
    }

    @Override
    public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) {
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Log.d("GEOFENCES", "Successfully added geofences");
        }
        workInProgress = false;
        mLocationClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("GEOFENCES", "connection failed");
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, 8878);
            } catch (IntentSender.SendIntentException e) {
                Log.d("GEOFENCES", "WTF");
            }
        }
    }
}
