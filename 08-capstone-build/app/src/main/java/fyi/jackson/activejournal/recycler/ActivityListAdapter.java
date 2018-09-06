package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;

public class ActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Activity> activities;

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
        ((ActivityViewHolder) viewHolder).bindTo(activities.get(position));
    }

    @Override
    public int getItemCount() {
        return (activities == null ? 0 : activities.size());
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
