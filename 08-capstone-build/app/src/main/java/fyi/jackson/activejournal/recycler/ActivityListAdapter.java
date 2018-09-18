package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.ui.ItemClickListener;

public class ActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ACTIVITY = 289;
    private static final int VIEW_TYPE_SPACER = 854;

    private List<Activity> activities;
    private ItemClickListener clickListener;

    public ActivityListAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_SPACER) {
            // Spacer Viewholder
            v = inflater.inflate(R.layout.view_holder_spacer, parent, false);
            viewHolder = new SpacerViewHolder(v);
        } else {
            v = inflater.inflate(R.layout.view_holder_activity, parent, false);
            viewHolder = new ActivityViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position == activities.size()) {
            // Spacer Viewholder
        } else {
            ((ActivityViewHolder) viewHolder).bindTo(activities.get(position), clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return (activities == null ? 0 : activities.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        return (activities.size() == position) ? VIEW_TYPE_SPACER : VIEW_TYPE_ACTIVITY;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
