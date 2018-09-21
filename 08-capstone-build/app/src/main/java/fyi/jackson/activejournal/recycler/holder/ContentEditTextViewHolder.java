package fyi.jackson.activejournal.recycler.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.ui.ContentClickListener;

public class ContentEditTextViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = ContentEditTextViewHolder.class.getSimpleName();

    public EditText text;

    public ContentEditTextViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.et_text_content);
    }

    public void bindTo(Content content, final ContentClickListener clickListener, final OnStartDragListener onStartDragListener) {
        text.setText(content.getValue());

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onStartDragListener.onStartDrag(ContentEditTextViewHolder.this);
                return false;
            }
        });
    }
}
