package com.harkin.luas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harkin.luas.network.models.Tram;

import java.util.List;

import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Tram> dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView destination;
        public final TextView time;

        public ViewHolder(View v) {
            super(v);
            destination = ButterKnife.findById(v, R.id.destination);
            time = ButterKnife.findById(v, R.id.time);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Tram> myDataset) {
        dataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stop, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.destination.setText(dataset.get(position).getDestination());
        holder.time.setText(dataset.get(position).getDueTime());

    }

    @Override public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override public int getItemCount() {
        return dataset.size();
    }
}