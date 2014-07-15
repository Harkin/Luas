package com.harkin.luas;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.harkin.luas.network.models.Timetable;
import com.harkin.luas.network.models.Tram;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.fabric.sdk.android.Fabric;

/**
 * @author Henry Larkin @harkinabout
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeLayout) SwipeRefreshLayout swipeLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.stopName) TextView stopName;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Presenter presenter;

    private final List<Tram> test = new ArrayList<>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setEnabled(true);

        presenter = new Presenter(this);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(test);
        mRecyclerView.setAdapter(adapter);

        presenter.loadStops();
    }

    @Override protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override public void onRefresh() {
        presenter.refresh();
    }

    @DebugLog public void displayTimes(String currStop, Timetable timetable) {
        swipeLayout.setRefreshing(false);

        stopName.setText(String.format(getString(R.string.stopName), currStop));

        test.clear();
        test.addAll(timetable.getInboundTrams());
        test.addAll(timetable.getOutboundTrams());
        adapter.notifyDataSetChanged();
    }
}
