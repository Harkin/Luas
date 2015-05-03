package com.harkin.luas;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.harkin.luas.models.api.StopTimetable;

import java.util.List;

/**
 * @author Henry Larkin @harkinabout
 */
public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;

    private Presenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setEnabled(false);

        presenter = new Presenter(this);

        BusWrapper.getInstance().register(presenter);
        BusWrapper.getInstance().register(this);

        presenter.loadStops();
    }

    @Override protected void onDestroy() {
        BusWrapper.getInstance().unregister(presenter);
        BusWrapper.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override public void onRefresh() {
        presenter.refresh();
        swipeLayout.setEnabled(false);
    }

    public void displayTimes(String currStop, List<StopTimetable> timetables) {
        ((TextView) findViewById(R.id.stopName)).setText(currStop);

        LinearLayout inbound = (LinearLayout) findViewById(R.id.inbound);
        LinearLayout outbound = (LinearLayout) findViewById(R.id.outbound);

        inbound.removeAllViews();
        outbound.removeAllViews();

        for (StopTimetable timetable : timetables) {
            LinearLayout ll;
            if (timetable.getDirection().equals("Outbound")) {
                ll = outbound;
            } else {
                ll = inbound;
            }

            View row = getLayoutInflater().inflate(R.layout.row_stop, ll);
            ((TextView) row.findViewById(R.id.destination)).setText(timetable.getDestination());
            ((TextView) row.findViewById(R.id.time)).setText(timetable.getDisplayTime());
        }

        swipeLayout.setEnabled(true);
    }
}
