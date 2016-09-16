package com.harkin.luas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.harkin.luas.network.models.Tram;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**
 * @author Henry Larkin @harkinabout
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeLayout) SwipeRefreshLayout swipeLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private final List<Tram> trams = new ArrayList<>();

    private RecyclerView.Adapter adapter;
    private Presenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setEnabled(true);

        presenter = new Presenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this, trams);
        mRecyclerView.setAdapter(adapter);

        presenter.loadStops();

        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onRefresh();
        }
    }

    @Override protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override public void onRefresh() {
        if (hasLocationPermission()) {
            presenter.refresh();
        }
    }

    @DebugLog public void displayTimes(String currStop, List<Tram> displayTrams) {
        swipeLayout.setRefreshing(false);

        toolbar.setSubtitle(currStop);

        trams.clear();
        trams.addAll(displayTrams);
        adapter.notifyDataSetChanged();
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
