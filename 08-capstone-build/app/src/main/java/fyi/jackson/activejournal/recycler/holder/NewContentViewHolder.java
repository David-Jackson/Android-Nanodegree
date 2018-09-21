package fyi.jackson.activejournal.recycler.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.ui.ContentClickListener;

public class NewContentViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = NewContentViewHolder.class.getSimpleName();

    public LinearLayout addTextLayout;
    public LinearLayout addImageLayout;
    public LinearLayout editContentLayout;

    public NewContentViewHolder(@NonNull View itemView) {
        super(itemView);
        addTextLayout = itemView.findViewById(R.id.layout_add_text);
        addImageLayout = itemView.findViewById(R.id.layout_add_image);
        editContentLayout = itemView.findViewById(R.id.layout_edit_content);
    }

    public void bindTo(Content content, final ContentClickListener clickListener, final OnStartDragListener onStartDragListener) {
        addTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
