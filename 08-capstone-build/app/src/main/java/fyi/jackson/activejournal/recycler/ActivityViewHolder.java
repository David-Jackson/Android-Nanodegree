package fyi.jackson.activejournal.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;

public class ActivityViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private ImageView type;

    public ActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_activity_title);
        type = itemView.findViewById(R.id.iv_activity_type);
    }

    public void bindTo(Activity activity) {
        name.setText(activity.getName());
    }
}
