package fyi.jackson.activejournal.recycler.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.ui.NewContentRowClickListener;

public class NewContentViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = NewContentViewHolder.class.getSimpleName();

    private LinearLayout addTextLayout;
    private LinearLayout addImageLayout;
    private LinearLayout editContentLayout;

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
