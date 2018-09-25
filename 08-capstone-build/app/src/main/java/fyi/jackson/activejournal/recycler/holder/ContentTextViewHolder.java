package fyi.jackson.activejournal.recycler.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.ui.ContentClickListener;

public class ContentTextViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = ContentTextViewHolder.class.getSimpleName();

    public TextView text;

    public ContentTextViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.tv_text_content);
    }

    public void bindTo(Content content, final OnStartDragListener onStartDragListener) {
        text.setText(content.getValue());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onStartDragListener.onStartDrag(ContentTextViewHolder.this);
                return false;
            }
        });
    }
}
