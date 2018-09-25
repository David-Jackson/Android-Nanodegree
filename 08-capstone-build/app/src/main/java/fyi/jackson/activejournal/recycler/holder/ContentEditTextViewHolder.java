package fyi.jackson.activejournal.recycler.holder;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fyi.jackson.activejournal.R;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.recycler.helper.OnStartDragListener;
import fyi.jackson.activejournal.ui.ContentChangeListener;

public class ContentEditTextViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = ContentEditTextViewHolder.class.getSimpleName();

    private Content content;
    public EditText text;
    private boolean firstChange;

    public ContentEditTextViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.et_text_content);
    }

    public void bindTo(final Content content, final ContentChangeListener changeListener, final OnStartDragListener onStartDragListener) {
        firstChange = true;
        this.content = content;

        text.setText(content.getValue());

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (firstChange) {
                    firstChange = false;
                    return;
                }
                Log.d(TAG, "afterTextChanged: TXTDEBUG : " + content.getUid() + " has changed.");
                content.setValue(editable.toString());
                changeListener.onChange(content);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onStartDragListener.onStartDrag(ContentEditTextViewHolder.this);
                return false;
            }
        });
    }

}
