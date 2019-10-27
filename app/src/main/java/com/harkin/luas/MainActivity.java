package com.harkin.luas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.harkin.luas.network.models.Tram;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private final List<Tram> trams = new ArrayList<>();

    private SwipeRefreshLayout swipeLayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private Presenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeLayout= findViewById(R.id.swipeLayout);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setEnabled(true);

        presenter = new Presenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this, trams);
        recyclerView.setAdapter(adapter);

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
