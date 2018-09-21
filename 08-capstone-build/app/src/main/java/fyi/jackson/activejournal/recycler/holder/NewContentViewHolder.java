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
import fyi.jackson.activejournal.ui.NewContentRowClickListener;

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

    public void bindTo(final NewContentRowClickListener clickListener) {
        addTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(NewContentRowClickListener.TYPE_ADD_TEXT);
            }
        });

        addImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(NewContentRowClickListener.TYPE_ADD_IMAGE);
            }
        });

        editContentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(NewContentRowClickListener.TYPE_EDIT_CONTENT);
            }
        });
    }
}
