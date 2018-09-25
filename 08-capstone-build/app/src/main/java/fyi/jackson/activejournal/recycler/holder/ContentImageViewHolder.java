package fyi.jackson.activejournal.recycler.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.ui.ContentClickListener;

public class ContentImageViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = ContentImageViewHolder.class.getSimpleName();

    public ImageView image;

    public ContentImageViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.iv_image_content);
    }

    public void bindTo(Content content, final OnStartDragListener onStartDragListener) {

        File f = new File(content.getValue());

        Picasso.get()
                .load(f)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: Error loading Content Image");
                        e.printStackTrace();
                    }
                });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onStartDragListener.onStartDrag(ContentImageViewHolder.this);
                return false;
            }
        });
    }
}
