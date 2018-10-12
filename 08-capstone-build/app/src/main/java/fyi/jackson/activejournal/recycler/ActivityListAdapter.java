package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.recycler.holder.ActivityViewHolder;
import fyi.jackson.activejournal.recycler.holder.EmptyListViewHolder;
import fyi.jackson.activejournal.ui.ItemClickListener;

public class ActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ACTIVITY = 289;
    private static final int VIEW_TYPE_EMPTY = 304;

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
        switch (viewType) {
            case VIEW_TYPE_ACTIVITY:
                v = inflater.inflate(R.layout.view_holder_activity, parent, false);
                viewHolder = new ActivityViewHolder(v);
                break;
            default: // VIEW_TYPE_EMPTY
                v = inflater.inflate(R.layout.view_holder_empty_list, parent, false);
                viewHolder = new EmptyListViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ACTIVITY:
                ((ActivityViewHolder) viewHolder).bindTo(activities.get(position), clickListener);
                break;
            case VIEW_TYPE_EMPTY:
                // TODO: 9/30/2018 set span size of empty view holder on tablet layouts to span the whole row whether this is done in the adapter, xml, or in a setSpanLookup
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (activities == null ? 0 : activities.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (activities.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ACTIVITY;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
