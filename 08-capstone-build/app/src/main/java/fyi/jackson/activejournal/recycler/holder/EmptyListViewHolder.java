package fyi.jackson.activejournal.recycler.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;

public class EmptyListViewHolder extends RecyclerView.ViewHolder {
    public EmptyListViewHolder(@NonNull View itemView) {
        super(itemView);

        ImageView type = itemView.findViewById(R.id.iv_activity_type);
        type.setImageResource(Activity.getRandomTypeResId());
    }
}
