package com.harkin.luas;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates.AdapterDelegatesManager;
import com.harkin.luas.network.models.Tram;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    private final AdapterDelegatesManager<List<Tram>> delegatesManager;
    private final List<Tram> trams;

    public MyAdapter(Activity activity, List<Tram> myDataset) {
        trams = myDataset;
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new LuasAdapterDelegate(activity.getLayoutInflater(), 0));
        delegatesManager.addDelegate(new HeaderAdapterDelegate(activity.getLayoutInflater(), 1));
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(trams, position, holder);
    }

    @Override public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(trams, position);
    }

    @Override public int getItemCount() {
        return trams.size();
    }
}
