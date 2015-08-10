package com.harkin.luas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.harkin.luas.network.models.Tram;

import java.util.List;

import butterknife.ButterKnife;

public class HeaderAdapterDelegate extends AbsAdapterDelegate<List<Tram>> {
    private final LayoutInflater inflater;

    public HeaderAdapterDelegate(LayoutInflater inflater, int viewType) {
        super(viewType);
        this.inflater = inflater;
    }

    @Override public boolean isForViewType(@NonNull List<Tram> trams, int position) {
        Tram tram = trams.get(position);
        return !tram.getDueTime().isEmpty() && tram.getDestination().isEmpty();
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.row_header, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull List<Tram> trams, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        Tram tram = trams.get(position);

        holder.message.setText(tram.getDueTime());
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView message;

        public HeaderViewHolder(View v) {
            super(v);
            message = ButterKnife.findById(v, R.id.destination);
        }
    }
}
