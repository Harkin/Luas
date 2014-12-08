package com.harkin.luas;

import android.app.Activity;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.harkin.luas.adapters.DrawerAdapter;
import com.harkin.luas.api.LuasAPI;
import com.harkin.luas.models.api.Luas;
import com.harkin.luas.models.api.LuasTimeResponse;
import com.harkin.luas.models.api.Stop;
import com.harkin.luas.models.api.StopModel;
import com.harkin.luas.models.api.StopResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements ExpandableListView.OnChildClickListener, SwipeRefreshLayout.OnRefreshListener {
    private List<StopModel> redline;
    private List<StopModel> greenline;

    private List<String> headers;
    private Map<String, List<String>> drawerListContents;

    private StopModel bestStop;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ExpandableListView drawerList;
    private DrawerAdapter drawerAdapter;

    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redline = new ArrayList<StopModel>();
        greenline = new ArrayList<StopModel>();
        headers = new ArrayList<String>();
        drawerListContents = new HashMap<String, List<String>>();

        headers.add("Red Line");
        headers.add("Green Line");

        drawerAdapter = new DrawerAdapter(this, headers, drawerListContents);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(drawerAdapter);
        drawerList.setOnChildClickListener(this);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayShowTitleEnabled(false);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Stops");
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        boolean gotStations = loadStations();
        if (gotStations) {
            findClosestStop();
            onRefresh();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private boolean loadStations() {
        //TODO change this. Should load from local storage
        //TODO add update service to regularly update stops. Every fortnight maybe?
        try {
            redline = new Select().from(StopModel.class).where("route = ?", "red").execute();
            greenline = new Select().from(StopModel.class).where("route = ?", "green").execute();
            updateDrawer();
        } catch (Exception e) {

        }
        boolean wtf = true;
        if (redline.size() == 0 || greenline.size() == 0) {
            System.out.println("Getting stops from API");
            LuasAPI.getInstance(this).getStops(luasSuccessListener(), errorListener());
            return false;
        }
        return true;
    }

    public Response.Listener<StopResponse> luasSuccessListener() {
        return new Response.Listener<StopResponse>() {
            @Override
            public void onResponse(StopResponse response) {
                for (Stop stop : response.getStops()) {
                    StopModel model = new StopModel(stop.getStopid(),
                            stop.getDisplaystopid().replace("LUAS", "").trim(),
                            stop.getLatitude(),
                            stop.getLongitude(),
                            stop.getLastupdated(),
                            stop.getOperators().get(0).getRoutes().get(0));

                    if (model.getRoute().equals("red")) {
                        redline.add(model);
                    } else {
                        greenline.add(model);
                    }
                }
                updateDrawer();
                saveStops();
                findClosestStop();
            }
        };
    }

    public Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        };
    }

    private void updateDrawer() {
        List<String> redLineNames = new ArrayList<String>();
        List<String> greenLineNames = new ArrayList<String>();

        for (StopModel s : redline) {
            redLineNames.add(s.getDisplaystopid());
        }

        for (StopModel s : greenline) {
            greenLineNames.add(s.getDisplaystopid());
        }

        drawerListContents.put("Red Line", redLineNames);
        drawerListContents.put("Green Line", greenLineNames);
        drawerAdapter.notifyDataSetChanged();
    }

    private void saveStops() {
        ActiveAndroid.beginTransaction();
        try {
            for (StopModel redModel : redline) {
                redModel.save();
            }
            for (StopModel greenModel : greenline) {
                greenModel.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void findClosestStop() {
        float[] results = new float[3];

        float closestStop = 1000000;

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location gps = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location network = lm.getLastKnownLocation((LocationManager.NETWORK_PROVIDER));
        Location best;
        if (gps != null && network != null) {
            if (gps.getTime() > network.getTime()) {
                best = gps;
            } else {
                best = network;
            }
        } else if (gps != null) {
            best = gps;
        } else if (network != null) {
            best = network;
        } else {
            //TODO if no last know loc available, poll for current loc.
            best = null;
        }


        for (StopModel s : redline) {
            Location.distanceBetween(best.getLatitude(), best.getLongitude(), s.getLatitude(), s.getLongitude(), results);
            if (results[0] < closestStop) {
                closestStop = results[0];
                bestStop = s;
            }
        }

        for (StopModel t : greenline) {
            Location.distanceBetween(best.getLatitude(), best.getLongitude(), t.getLatitude(), t.getLongitude(), results);
            if (results[0] < closestStop) {
                closestStop = results[0];
                bestStop = t;
            }
        }

        onRefresh();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l) {
        List<StopModel> selectedList;
        if (groupPosition == 0) {
            selectedList = redline;
        } else {
            selectedList = greenline;
        }
        bestStop = selectedList.get(childPosition);
        onRefresh();
        drawerLayout.closeDrawers();
        return true;
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

    @Override
    public void onRefresh() {
        LuasAPI.getInstance(this).getLuasTimes(bestStop.getStopid(), timesSuccessListener(), errorListener());

    }

    private void displayTimes(List<Luas> luas) {
        ((TextView) findViewById(R.id.stopName)).setText(bestStop.getDisplaystopid());
        LinearLayout inbound = (LinearLayout) findViewById(R.id.inbound);
        LinearLayout outbound = (LinearLayout) findViewById(R.id.outbound);

        inbound.removeAllViews();
        outbound.removeAllViews();

        for (Luas l : luas) {
            View row = getLayoutInflater().inflate(R.layout.row_stop, null);
            ((TextView) row.findViewById(R.id.destination)).setText(l.getDestination());
            ((TextView) row.findViewById(R.id.time)).setText(l.getDuetime());

            if (l.getDirection().equals("Outbound")) {
                outbound.addView(row);
            } else {
                inbound.addView(row);
            }
        }
    }

    public Response.Listener<LuasTimeResponse> timesSuccessListener() {
        return new Response.Listener<LuasTimeResponse>() {
            @Override
            public void onResponse(LuasTimeResponse response) {
                swipeLayout.setRefreshing(false);
                displayTimes(response.getResults());
            }
        };
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        //hide menu items here
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
