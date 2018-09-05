package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fyi.jackson.activejournal.R;

public class ActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ActivityListAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_holder_activity, parent, false);
        return new ActivityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
