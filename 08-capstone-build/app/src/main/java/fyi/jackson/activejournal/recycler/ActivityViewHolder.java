package fyi.jackson.activejournal.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.worker.ImageSaver;

public class ActivityViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = ActivityViewHolder.class.getSimpleName();

    private TextView name;
    private ImageView type;
    private ImageView map;

    public ActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_activity_title);
        type = itemView.findViewById(R.id.iv_activity_type);
        map = itemView.findViewById(R.id.iv_activity_map);
    }

    public void bindTo(Activity activity) {
        name.setText(activity.getName());
        type.setImageResource(activity.getTypeResId());

        if (activity.getThumbnail() != null) {
            File f = new File(
                    itemView.getContext().getDir("thumbnails", Context.MODE_PRIVATE),
                    activity.getThumbnail());

            Picasso.get()
                    .load(f)
                    .placeholder(R.drawable.image_activity_map_placeholder)
                    .error(R.drawable.image_activity_map_placeholder)
                    .into(map);
        } else {
            map.setImageResource(R.drawable.image_activity_map_placeholder);
        }
    }
}
