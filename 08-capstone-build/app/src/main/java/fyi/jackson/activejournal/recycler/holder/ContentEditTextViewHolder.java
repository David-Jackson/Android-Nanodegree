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

    long inputFinishDelay = 1000; // 1 seconds after user stops typing
    long lastTextEdit = 0;
    Handler handler;
    private Runnable inputFinishChecker;

    public ContentEditTextViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.et_text_content);
    }

    public void bindTo(final Content content, final ContentChangeListener changeListener, final OnStartDragListener onStartDragListener) {
        this.content = content;

        handler = new Handler();
        inputFinishChecker = new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() > (lastTextEdit + inputFinishDelay - 500)) {
                    Toast.makeText(itemView.getContext(), "Changing content", Toast.LENGTH_SHORT).show();
                    content.setValue(text.getText().toString());
                    changeListener.onChange(content);
                }
            }
        };

        text.setText(content.getValue());

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(inputFinishChecker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                lastTextEdit = System.currentTimeMillis();
                handler.postDelayed(inputFinishChecker, inputFinishDelay);
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
